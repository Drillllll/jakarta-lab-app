package com.demo.rest.modules.player.repository.implementation;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.repository.api.PlayerRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class PlayerRepositoryInMemory implements PlayerRepository {

    private final DataStore store;

    @Inject
    public PlayerRepositoryInMemory(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Player> find(UUID id) {
        return store.findAllPlayers().stream()
                .filter(player -> player.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Player> findAll() {
        return store.findAllPlayers();
    }

    @Override
    public void create(Player entity) {
        store.createPlayer(entity);
    }

    @Override
    public void delete(Player entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(Player entity) {
        store.updatePlayer(entity);
    }

    @Override
    public Optional<Player> findByLogin(String login) {
        return store.findAllPlayers().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

}
