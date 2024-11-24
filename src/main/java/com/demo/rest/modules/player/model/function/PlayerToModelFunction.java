package com.demo.rest.modules.player.model.function;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.model.PlayerModel;

import java.io.Serializable;
import java.util.function.Function;

public class PlayerToModelFunction implements Function<Player, PlayerModel>, Serializable {

    @Override
    public PlayerModel apply(Player entity) {
        return PlayerModel.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }
}
