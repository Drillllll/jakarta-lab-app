package com.demo.rest.player.dto.function;

import com.demo.rest.player.dto.PutPlayerRequest;
import com.demo.rest.player.entity.Player;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPlayerFunction implements BiFunction<UUID, PutPlayerRequest, Player> {

    @Override
    public Player apply(UUID id, PutPlayerRequest request) {
        return Player.builder()
                .id(id)
                .login(request.getLogin())
                .username(request.getUsername())
                .birthDate(request.getBirthDate())
                .heroName(request.getHeroName())
                .password(request.getPassword())
                .build();
    }
}
