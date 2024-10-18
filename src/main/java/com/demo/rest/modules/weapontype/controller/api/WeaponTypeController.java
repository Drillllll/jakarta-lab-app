package com.demo.rest.modules.weapontype.controller.api;

import com.demo.rest.modules.weapontype.dto.GetWeaponTypeResponse;
import com.demo.rest.modules.weapontype.dto.GetWeaponTypesResponse;
import com.demo.rest.modules.weapontype.dto.PatchWeaponTypeRequest;
import com.demo.rest.modules.weapontype.dto.PutWeaponTypeRequest;

import java.util.UUID;

public interface WeaponTypeController {

    GetWeaponTypesResponse getWeaponTypes();

    GetWeaponTypeResponse getWeaponType(UUID id);

    void putWeaponType(UUID id, PutWeaponTypeRequest request);

    void deleteWeaponType(UUID id);

    void patchWeaponType(UUID id, PatchWeaponTypeRequest request);
}
