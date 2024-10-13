package com.demo.rest.modules.weapon.entity;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode
public class Weapon implements Serializable {

    private UUID id;

    private String name;
    private Integer damage;
    private Integer weight;
    private Integer value;

    private WeaponType weaponType;
    private Player player;
}


