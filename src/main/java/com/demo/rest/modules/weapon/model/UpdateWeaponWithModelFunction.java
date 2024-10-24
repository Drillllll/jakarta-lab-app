package com.demo.rest.modules.weapon.model;

import com.demo.rest.modules.weapon.entity.Weapon;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateWeaponWithModelFunction implements BiFunction<Weapon, WeaponEditModel, Weapon>, Serializable {

    @Override
    @SneakyThrows
    public Weapon apply(Weapon entity, WeaponEditModel request) {
        return Weapon.builder()
                .id(entity.getId())
                .name(request.getName())
                .weight(request.getWeight())
                .value(entity.getValue())
                .damage(entity.getDamage())
                .weaponType(entity.getWeaponType())
                .build();
    }
}
