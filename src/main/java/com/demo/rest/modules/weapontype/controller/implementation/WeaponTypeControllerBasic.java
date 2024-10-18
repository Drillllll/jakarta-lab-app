package com.demo.rest.modules.weapontype.controller.implementation;

import com.demo.rest.controller.servlet.exception.BadRequestException;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.weapontype.controller.api.WeaponTypeController;
import com.demo.rest.modules.weapontype.dto.GetWeaponTypeResponse;
import com.demo.rest.modules.weapontype.dto.GetWeaponTypesResponse;
import com.demo.rest.modules.weapontype.dto.PatchWeaponTypeRequest;
import com.demo.rest.modules.weapontype.dto.PutWeaponTypeRequest;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class WeaponTypeControllerBasic implements WeaponTypeController {

    private final WeaponTypeService service;
    private final DtoFunctionFactory factory;

    @Inject
    public WeaponTypeControllerBasic(WeaponTypeService weaponTypeService, DtoFunctionFactory factory) {
        this.service = weaponTypeService;
        this.factory = factory;
    }

    @Override
    public GetWeaponTypesResponse getWeaponTypes() {
        return factory.weaponTypesToResponse().apply(service.findAll());
    }

    @Override
    public GetWeaponTypeResponse getWeaponType(UUID id) {
        return service.find(id)
                .map(factory.weaponTypeToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putWeaponType(UUID id, PutWeaponTypeRequest request) {
        try {
            service.create(factory.requestToWeaponType().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteWeaponType(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchWeaponType(UUID id, PatchWeaponTypeRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateWeaponType().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
