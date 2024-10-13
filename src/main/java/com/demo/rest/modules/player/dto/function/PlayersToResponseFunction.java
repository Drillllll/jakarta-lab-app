package com.demo.rest.modules.player.dto.function;

import com.demo.rest.modules.player.dto.GetPlayersResponse;
import com.demo.rest.modules.player.entity.Player;

import java.util.List;
import java.util.function.Function;

public class PlayersToResponseFunction implements Function<List<Player>, GetPlayersResponse> {
    @Override
    public GetPlayersResponse apply(List<Player> players) {
        return GetPlayersResponse.builder()
                .players(players.stream()
                        .map(player -> GetPlayersResponse.Player.builder()
                                .id(player.getId())
                                .login(player.getLogin())
                                .username(player.getUsername())
                                .heroName(player.getHeroName())
                                .build())
                        .toList())
                .build();
    }
}
