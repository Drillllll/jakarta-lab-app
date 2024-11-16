package com.demo.rest.modules.weapontype.entity;

import com.demo.rest.modules.weapon.entity.Weapon;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
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
@Table(name = "weapontypes")
public class WeaponType implements Serializable {

    @Id
    private UUID id;

    private String name;
    private Float speed;
    private Boolean isTwoHanded;
    private DamageType damageType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "weaponType", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Weapon> weapons;


}

