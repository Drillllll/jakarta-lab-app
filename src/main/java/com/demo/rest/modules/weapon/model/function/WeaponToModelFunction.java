package com.demo.rest.modules.weapon.model.function;

import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.model.WeaponModel;

import java.io.Serializable;
import java.util.function.Function;

public class WeaponToModelFunction implements Function<Weapon, WeaponModel>, Serializable {

    @Override
    public WeaponModel apply(Weapon entity) {
        return WeaponModel.builder()
                .name(entity.getName())
                .damage(entity.getDamage())
                .weight(entity.getWeight())
                .value(entity.getValue())
                .build();
    }
}
