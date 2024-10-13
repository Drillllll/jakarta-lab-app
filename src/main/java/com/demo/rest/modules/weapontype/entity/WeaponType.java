package com.demo.rest.modules.weapontype.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode
public class WeaponType implements Serializable {

    private UUID id;

    private String name;
    private Float speed;
    private Boolean isTwoHanded;
    private DamageType damageType;

}

