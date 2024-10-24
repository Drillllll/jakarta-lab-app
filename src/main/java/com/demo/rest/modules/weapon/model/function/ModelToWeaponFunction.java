package com.demo.rest.modules.weapon.model.function;

import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.model.WeaponCreateModel;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToWeaponFunction implements Function<WeaponCreateModel, Weapon>, Serializable {

    @Override
    @SneakyThrows
    public Weapon apply(WeaponCreateModel model) {
        return Weapon.builder()
                .id(model.getId())
                .name(model.getName())
                .weight(model.getWeight())
                .value(model.getValue())
                .damage(model.getDamage())
                .weaponType(WeaponType.builder()
                        .id(model.getWeaponType().getId())
                        .build())
                .build();
    }
}
