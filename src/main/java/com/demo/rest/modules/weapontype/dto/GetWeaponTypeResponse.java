package com.demo.rest.modules.weapontype.dto;

import com.demo.rest.modules.weapontype.entity.DamageType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetWeaponTypeResponse {
    private UUID id;

    private String name;
    private Float speed;
    private Boolean isTwoHanded;
    private DamageType damageType;

}
