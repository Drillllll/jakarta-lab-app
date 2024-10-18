package com.demo.rest.modules.weapontype.dto.function;

import com.demo.rest.modules.weapontype.dto.GetWeaponTypesResponse;
import com.demo.rest.modules.weapontype.entity.WeaponType;

import java.util.List;
import java.util.function.Function;

public class WeaponTypesToResponseFunction implements Function<List<WeaponType>, GetWeaponTypesResponse> {
    @Override
    public GetWeaponTypesResponse apply(List<WeaponType> weaponTypes) {
        return GetWeaponTypesResponse.builder()
                .weaponTypes(weaponTypes.stream()
                        .map(weaponType -> GetWeaponTypesResponse.WeaponType.builder()
                                .name(weaponType.getName())
                                .damageType(weaponType.getDamageType())
                                .build())
                        .toList())
                .build();
    }
}
