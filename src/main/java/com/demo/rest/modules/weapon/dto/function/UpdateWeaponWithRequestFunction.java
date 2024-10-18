package com.demo.rest.modules.weapon.dto.function;

import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.entity.Weapon;


import java.util.function.BiFunction;

public class UpdateWeaponWithRequestFunction implements BiFunction<Weapon, PatchWeaponRequest, Weapon> {

    @Override
    public Weapon apply(Weapon entity, PatchWeaponRequest request) {
        return Weapon.builder()
                .id(entity.getId())
                .name(request.getName())
                .damage(request.getDamage())
                .value(request.getValue())
                .weight(request.getWeight())
                .player(entity.getPlayer())
                .weaponType(entity.getWeaponType())
                .build();
    }
}