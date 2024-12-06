package com.demo.rest.modules.weapon.model;

import com.demo.rest.modules.player.model.PlayerModel;
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
public class WeaponEditModel {

    private String name;
    private Integer damage;
    private Integer weight;
    private Integer value;

    private PlayerModel player;

    private Long version;

}
