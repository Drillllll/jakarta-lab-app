package com.demo.rest.modules.weapontype.entity;

import com.demo.rest.modules.weapon.entity.Weapon;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;


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
    @OneToMany(mappedBy = "weaponType", cascade = CascadeType.REMOVE)
    private List<Weapon> weapons;


}

