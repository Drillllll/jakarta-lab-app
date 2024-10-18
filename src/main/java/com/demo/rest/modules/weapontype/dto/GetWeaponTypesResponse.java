package com.demo.rest.modules.weapontype.dto;

import com.demo.rest.modules.weapontype.entity.DamageType;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetWeaponTypesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class WeaponType {

        private UUID id;

        private String name;
        private DamageType damageType;
    }

    @Singular
    private List<WeaponType> weaponTypes;
}
