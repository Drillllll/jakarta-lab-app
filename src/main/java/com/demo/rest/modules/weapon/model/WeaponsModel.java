package com.demo.rest.modules.weapon.model;

import com.demo.rest.modules.weapontype.model.WeaponTypesModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class WeaponsModel implements Serializable{

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Weapon {

        private UUID id;
        private String name;

    }

    @Singular
    private List<WeaponsModel.Weapon> weapons;
}
