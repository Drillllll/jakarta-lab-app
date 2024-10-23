package com.demo.rest.modules.weapon.model.function;

import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.model.WeaponsModel;

import java.util.List;
import java.util.function.Function;

public class WeaponsToModelFunction implements Function<List<Weapon>, WeaponsModel> {

    @Override
    public WeaponsModel apply(List<Weapon> entity) {
        return WeaponsModel.builder()
                .weapons(entity.stream()
                        .map(weapon -> WeaponsModel.Weapon.builder()
                                .id(weapon.getId())
                                .name(weapon.getName())
                                .build())
                        .toList())
                .build();
    }

}
