package com.demo.rest.modules.weapon.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchWeaponRequest {

    private String name;
    private Integer damage;
    private Integer weight;
    private Integer value;

    private Long version;
}
