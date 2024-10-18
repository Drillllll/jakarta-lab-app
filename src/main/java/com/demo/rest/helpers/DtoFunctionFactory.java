package com.demo.rest.helpers;

import com.demo.rest.modules.player.dto.function.PlayerToResponseFunction;
import com.demo.rest.modules.player.dto.function.PlayersToResponseFunction;
import com.demo.rest.modules.player.dto.function.RequestToPlayerFunction;
import com.demo.rest.modules.player.dto.function.UpdatePlayerWithRequestFunction;
import com.demo.rest.modules.weapon.dto.function.RequestToWeaponFunction;
import com.demo.rest.modules.weapon.dto.function.UpdateWeaponWithRequestFunction;
import com.demo.rest.modules.weapon.dto.function.WeaponToResponseFunction;
import com.demo.rest.modules.weapon.dto.function.WeaponsToResponseFunction;
import com.demo.rest.modules.weapontype.dto.function.RequestToWeaponTypeFunction;
import com.demo.rest.modules.weapontype.dto.function.UpdateWeaponTypeWithRequestFunction;
import com.demo.rest.modules.weapontype.dto.function.WeaponTypeToResponseFunction;
import com.demo.rest.modules.weapontype.dto.function.WeaponTypesToResponseFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoFunctionFactory {

    // player

    public PlayersToResponseFunction playersToResponse() {
        return new PlayersToResponseFunction();
    }

    public PlayerToResponseFunction playerToResponse() {
        return new PlayerToResponseFunction();
    }

    public RequestToPlayerFunction requestToPlayer() {
        return new RequestToPlayerFunction();
    }

    public UpdatePlayerWithRequestFunction updatePlayer() {
        return new UpdatePlayerWithRequestFunction();
    }

    // weapon

    public WeaponsToResponseFunction weaponsToResponse() {
        return new WeaponsToResponseFunction();
    }

    public WeaponToResponseFunction weaponToResponse() {
        return new WeaponToResponseFunction();
    }

    public RequestToWeaponFunction requestToWeapon() {
        return new RequestToWeaponFunction();
    }

    public UpdateWeaponWithRequestFunction updateWeapon() {
        return new UpdateWeaponWithRequestFunction();
    }

    // weapon type

    public WeaponTypesToResponseFunction weaponTypesToResponse() {
        return new WeaponTypesToResponseFunction();
    }

    public WeaponTypeToResponseFunction weaponTypeToResponse() {
        return new WeaponTypeToResponseFunction();
    }

    public RequestToWeaponTypeFunction requestToWeaponType() {
        return new RequestToWeaponTypeFunction();
    }

    public UpdateWeaponTypeWithRequestFunction updateWeaponType() {
        return new UpdateWeaponTypeWithRequestFunction();
    }
}
