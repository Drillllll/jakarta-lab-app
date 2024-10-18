package com.demo.rest.modules.weapontype.dto;

import com.demo.rest.modules.weapontype.entity.DamageType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchWeaponTypeRequest {

    private String name;
    private Float speed;
    private Boolean isTwoHanded;
    private DamageType damageType;
}
