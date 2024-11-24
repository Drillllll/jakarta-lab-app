package com.demo.rest.modules.weapon.model;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapon.entity.Weapon;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateWeaponWithModelFunction implements BiFunction<Weapon, WeaponEditModel, Weapon>, Serializable {

    @Override
    @SneakyThrows
    public Weapon apply(Weapon entity, WeaponEditModel model) {
        return Weapon.builder()
                .id(entity.getId())
                .name(model.getName())
                .weight(model.getWeight())
                .value(model.getValue())
                .damage(model.getDamage())
                .weaponType(entity.getWeaponType())
                .player(Player.builder()
                        .id(model.getPlayer().getId())
                        .build())
                .build();
    }
}
