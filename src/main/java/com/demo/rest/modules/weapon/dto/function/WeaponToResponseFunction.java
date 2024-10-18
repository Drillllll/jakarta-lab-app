package com.demo.rest.modules.weapon.dto.function;

import com.demo.rest.modules.weapon.dto.GetWeaponResponse;
import com.demo.rest.modules.weapon.entity.Weapon;

import java.util.function.Function;

public class WeaponToResponseFunction implements Function<Weapon, GetWeaponResponse> {

    @Override
    public GetWeaponResponse apply(Weapon entity) {
        return GetWeaponResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .damage(entity.getDamage())
                .value(entity.getValue())
                .weight(entity.getWeight())
                .build();
    }
}
