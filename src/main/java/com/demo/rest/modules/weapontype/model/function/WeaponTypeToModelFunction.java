package com.demo.rest.modules.weapontype.model.function;

import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.model.WeaponTypeModel;

import java.io.Serializable;
import java.util.function.Function;

public class WeaponTypeToModelFunction implements Function<WeaponType, WeaponTypeModel>, Serializable {

    @Override
    public WeaponTypeModel apply(WeaponType entity) {
        return WeaponTypeModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isTwoHanded(entity.getIsTwoHanded())
                .speed(entity.getSpeed())
                .damageType(entity.getDamageType())
                .build();
    }
}
