package com.demo.rest.modules.player.dto.function;

import com.demo.rest.modules.player.dto.GetPlayerResponse;
import com.demo.rest.modules.player.entity.Player;

import java.util.function.Function;

public class PlayerToResponseFunction  implements Function<Player, GetPlayerResponse> {
    @Override
    public GetPlayerResponse apply(Player player) {
        return GetPlayerResponse.builder()
                .id(player.getId())
                .login(player.getLogin())
                .username(player.getUsername())
                .birthDate(player.getBirthDate())
                .heroName(player.getHeroName())
                .roles(player.getRoles())
                .build();
    }
}
