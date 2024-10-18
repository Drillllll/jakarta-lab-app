package com.demo.rest.modules.weapon.dto.function;

import com.demo.rest.modules.weapon.dto.GetWeaponsResponse;
import com.demo.rest.modules.weapon.entity.Weapon;

import java.util.List;
import java.util.function.Function;

public class WeaponsToResponseFunction  implements Function<List<Weapon>, GetWeaponsResponse> {
    @Override
    public GetWeaponsResponse apply(List<Weapon> weapons) {
        return GetWeaponsResponse.builder()
                .weapons(weapons.stream()
                        .map(weapon -> GetWeaponsResponse.Weapon.builder()
                                .id(weapon.getId())
                                .name(weapon.getName())
                                .damage(weapon.getDamage())
                                .build())
                        .toList())
                .build();
    }
}
