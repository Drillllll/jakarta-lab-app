package com.demo.rest.modules.weapon.controller.implementation;

import com.demo.rest.controller.servlet.exception.BadRequestException;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.weapon.controller.api.WeaponController;
import com.demo.rest.modules.weapon.dto.GetWeaponResponse;
import com.demo.rest.modules.weapon.dto.GetWeaponsResponse;
import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;
import com.demo.rest.modules.weapon.service.WeaponService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class WeaponControllerBasic implements WeaponController {

    private final WeaponService service;
    private final DtoFunctionFactory factory;

    @Inject
    public WeaponControllerBasic(WeaponService weaponService, DtoFunctionFactory factory) {
        this.service = weaponService;
        this.factory = factory;
    }

    @Override
    public GetWeaponsResponse getWeapons() {
        return factory.weaponsToResponse().apply(service.findAll());
    }

    @Override
    public GetWeaponResponse getWeapon(UUID id) {
        return service.find(id)
                .map(factory.weaponToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putWeapon(UUID id, PutWeaponRequest request) {
        try {
            service.create(factory.requestToWeapon().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteWeapon(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchWeapon(UUID id, PatchWeaponRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateWeapon().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
