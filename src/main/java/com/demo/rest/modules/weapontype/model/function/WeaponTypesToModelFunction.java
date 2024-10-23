package com.demo.rest.modules.weapontype.model.function;

import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.model.WeaponTypesModel;

import java.util.List;
import java.util.function.Function;

public class WeaponTypesToModelFunction implements Function<List<WeaponType>, WeaponTypesModel> {

    @Override
    public WeaponTypesModel apply(List<WeaponType> entity) {
        return WeaponTypesModel.builder()
                .weaponTypes(entity.stream()
                        .map(weaponType -> WeaponTypesModel.WeaponType.builder()
                                .id(weaponType.getId())
                                .name(weaponType.getName())
                                .build())
                        .toList())
                .build();
    }

}
