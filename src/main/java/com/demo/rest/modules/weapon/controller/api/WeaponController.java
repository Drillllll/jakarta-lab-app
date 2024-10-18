package com.demo.rest.modules.weapon.controller.api;

import com.demo.rest.modules.weapon.dto.GetWeaponResponse;
import com.demo.rest.modules.weapon.dto.GetWeaponsResponse;
import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;

import java.util.UUID;

public interface WeaponController {

    GetWeaponsResponse getWeapons();

    GetWeaponResponse getWeapon(UUID id);

    void putWeapon(UUID id, PutWeaponRequest request);

    void deleteWeapon(UUID id);

    void patchWeapon(UUID id, PatchWeaponRequest request);

}
