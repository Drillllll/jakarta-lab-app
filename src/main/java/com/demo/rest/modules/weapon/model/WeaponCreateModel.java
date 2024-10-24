package com.demo.rest.modules.weapon.model;

import com.demo.rest.modules.weapontype.model.WeaponTypeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class WeaponCreateModel {

    private UUID id;

    private String name;
    private Integer damage;
    private Integer weight;
    private Integer value;

    private WeaponTypeModel weaponType;

}
