package com.demo.rest.modules.weapontype.dto.function;

import com.demo.rest.modules.weapontype.dto.PatchWeaponTypeRequest;
import com.demo.rest.modules.weapontype.entity.WeaponType;

import java.util.function.BiFunction;

public class UpdateWeaponTypeWithRequestFunction implements BiFunction<WeaponType, PatchWeaponTypeRequest, WeaponType> {

    @Override
    public WeaponType apply(WeaponType entity, PatchWeaponTypeRequest request) {
        return WeaponType.builder()
                .id(entity.getId())
                .name(request.getName())
                .speed(request.getSpeed())
                .isTwoHanded(request.getIsTwoHanded())
                .damageType(request.getDamageType())
                .build();
    }
}