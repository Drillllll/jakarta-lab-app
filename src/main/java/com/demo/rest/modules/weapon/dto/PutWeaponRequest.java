package com.demo.rest.modules.weapon.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutWeaponRequest {

    private UUID id;

    private String name;
    private Integer damage;
    private Integer weight;
    private Integer value;

    private UUID weaponType;
    private UUID player;

    private Long version;
}
