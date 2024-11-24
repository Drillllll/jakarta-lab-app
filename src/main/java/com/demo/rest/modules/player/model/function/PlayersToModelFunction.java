package com.demo.rest.modules.player.model.function;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.model.PlayersModel;

import java.util.List;
import java.util.function.Function;

public class PlayersToModelFunction implements Function<List<Player>, PlayersModel> {
    @Override
    public PlayersModel apply(List<Player> entity) {
        return PlayersModel.builder()
                .players(entity.stream()
                        .map(player -> PlayersModel.Player.builder()
                                .id(player.getId())
                                .login(player.getLogin())
                                .build())
                        .toList())
                .build();
    }
}
