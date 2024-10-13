package com.demo.rest.datastore;

import com.demo.rest.helpers.CloningUtility;
import com.demo.rest.player.entity.Player;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log
public class DataStore {

    private final CloningUtility cloningUtility;

    private final Set<Player> players = new HashSet<>();

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /** user */

    public synchronized List<Player> findAllPlayers() {
        return players.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createPlayer(Player value) throws IllegalArgumentException {
        if (players.stream().anyMatch(player -> player.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The player id \"%s\" is not unique".formatted(value.getId()));
        }
        players.add(cloningUtility.clone(value));
    }

    public synchronized void updatePlayer(Player value) throws IllegalArgumentException {
        if (players.removeIf(player -> player.getId().equals(value.getId()))) {
            players.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The player with id \"%s\" does not exist".formatted(value.getId()));
        }
    }
}
