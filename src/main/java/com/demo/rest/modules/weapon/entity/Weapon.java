package com.demo.rest.modules.weapon.entity;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import jakarta.persistence.*;
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
@Entity
@Table(name = "weapons")
public class Weapon implements Serializable {

    @Id
    private UUID id;

    private String name;
    private Integer damage;
    private Integer weight;

    @Column(name = "\"VALUE\"")
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "weaponType")
    private WeaponType weaponType;

    @ManyToOne
    @JoinColumn(name = "player_username")
    private Player player;
}


