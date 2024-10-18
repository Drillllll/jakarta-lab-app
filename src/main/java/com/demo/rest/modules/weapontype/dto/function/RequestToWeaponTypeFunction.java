package com.demo.rest.modules.weapontype.dto.function;

import com.demo.rest.modules.weapontype.dto.PutWeaponTypeRequest;
import com.demo.rest.modules.weapontype.entity.WeaponType;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToWeaponTypeFunction implements BiFunction<UUID, PutWeaponTypeRequest, WeaponType> {

    @Override
    public WeaponType apply(UUID id, PutWeaponTypeRequest request) {
        return WeaponType.builder()
                .id(id)
                .name(request.getName())
                .speed(request.getSpeed())
                .isTwoHanded(request.getIsTwoHanded())
                .damageType(request.getDamageType())
                .build();
    }
}
