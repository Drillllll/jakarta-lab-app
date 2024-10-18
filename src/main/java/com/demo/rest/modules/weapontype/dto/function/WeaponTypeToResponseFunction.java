package com.demo.rest.modules.weapontype.dto.function;

import com.demo.rest.modules.weapontype.dto.GetWeaponTypeResponse;
import com.demo.rest.modules.weapontype.entity.WeaponType;

import java.util.function.Function;

public class WeaponTypeToResponseFunction implements Function<WeaponType, GetWeaponTypeResponse> {

    @Override
    public GetWeaponTypeResponse apply(WeaponType entity) {
        return GetWeaponTypeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .speed(entity.getSpeed())
                .isTwoHanded(entity.getIsTwoHanded())
                .damageType(entity.getDamageType())
                .build();
    }
}
