package com.demo.rest.modules.player.repository.api;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.repository.api.Repository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends Repository<Player, UUID> {

    Optional<Player> findByLogin(String login);
}