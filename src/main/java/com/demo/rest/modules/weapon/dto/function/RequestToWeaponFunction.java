package com.demo.rest.modules.weapon.dto.function;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapontype.entity.WeaponType;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToWeaponFunction implements BiFunction<UUID, PutWeaponRequest, Weapon> {

    @Override
    public Weapon apply(UUID id, PutWeaponRequest request) {
        return Weapon.builder()
                .id(id)
                .name(request.getName())
                .damage(request.getDamage())
                .value(request.getValue())
                .weight(request.getWeight())
                .player(Player.builder().id(request.getPlayer()).build())
                .weaponType(WeaponType.builder().id(request.getWeaponType()).build())
                .build();
    }
}