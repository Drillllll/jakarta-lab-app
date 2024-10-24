package com.demo.rest.helpers;

import com.demo.rest.modules.weapon.model.function.ModelToWeaponFunction;
import com.demo.rest.modules.weapon.model.function.WeaponToModelFunction;
import com.demo.rest.modules.weapon.model.function.WeaponsToModelFunction;
import com.demo.rest.modules.weapontype.model.function.WeaponTypeToModelFunction;
import com.demo.rest.modules.weapontype.model.function.WeaponTypesToModelFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelFunctionFactory {

    public WeaponTypesToModelFunction weaponTypesToModel() {return new WeaponTypesToModelFunction();}
    public WeaponTypeToModelFunction weaponTypeToModel() {return new WeaponTypeToModelFunction();}

    public WeaponsToModelFunction weaponsToModel() {return new WeaponsToModelFunction();}
    public WeaponToModelFunction weaponToModel() {return new WeaponToModelFunction();}
    public ModelToWeaponFunction modelToWeapon() {return new ModelToWeaponFunction();}
}
