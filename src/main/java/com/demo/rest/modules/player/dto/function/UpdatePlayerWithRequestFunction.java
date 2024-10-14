package com.demo.rest.modules.player.dto.function;

import com.demo.rest.modules.player.dto.PatchPlayerRequest;
import com.demo.rest.modules.player.entity.Player;

import java.util.function.BiFunction;

public class UpdatePlayerWithRequestFunction implements BiFunction<Player, PatchPlayerRequest, Player> {

    @Override
    public Player apply(Player entity, PatchPlayerRequest request) {
        return Player.builder()
                .id(entity.getId())
                .username(request.getUsername())
                .login(request.getLogin())
                .birthDate(request.getBirthDate())
                .heroName(request.getHeroName())
                .build();
    }
}

