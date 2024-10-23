package com.demo.rest.modules.weapontype.model;

import com.demo.rest.modules.weapontype.entity.DamageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class WeaponTypeModel {

    private String name;
    private Float speed;
    private Boolean isTwoHanded;
    private DamageType damageType;
}
