package com.demo.rest.modules.weapon.model.function;

import com.demo.rest.modules.player.model.function.PlayerToModelFunction;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.model.WeaponEditModel;

import java.io.Serializable;
import java.util.function.Function;


public class WeaponToEditModelFunction  implements Function<Weapon, WeaponEditModel>, Serializable {

    private final PlayerToModelFunction playerToModelFunction;

    public WeaponToEditModelFunction(PlayerToModelFunction playerToModelFunction) {
        this.playerToModelFunction = playerToModelFunction;
    }

    @Override
    public WeaponEditModel apply(Weapon entity) {
        return WeaponEditModel.builder()
                .name(entity.getName())
                .weight(entity.getWeight())
                .value(entity.getValue())
                .damage(entity.getDamage())
                .player(playerToModelFunction.apply(entity.getPlayer()))
                .version(entity.getVersion())
                .build();
    }
}
