package com.demo.rest.modules.weapon.controller.implementation;

import com.demo.rest.modules.weapontype.controller.api.WeaponTypeController;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.weapon.controller.api.WeaponController;
import com.demo.rest.modules.weapon.dto.GetWeaponResponse;
import com.demo.rest.modules.weapon.dto.GetWeaponsResponse;
import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;
import com.demo.rest.modules.weapon.service.WeaponService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import jakarta.inject.Inject;

import java.util.UUID;

@Path("")
public class WeaponControllerRest implements WeaponController {

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    private final WeaponService service;
    private final DtoFunctionFactory factory;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public WeaponControllerRest(
            WeaponService weaponService,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = weaponService;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetWeaponsResponse getWeapons() {
        return factory.weaponsToResponse().apply(service.findAll());
    }

    @Override
    public GetWeaponResponse getWeapon(UUID weaponTypeId, UUID weaponId) {
        return service.find(weaponTypeId, weaponId)
                .map(factory.weaponToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putWeapon(UUID weaponTypeId, UUID weaponId, PutWeaponRequest request) {
        try {
            service.create(factory.requestToWeapon().apply(weaponId, request), weaponTypeId);

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }  catch (NotFoundException ex) {
            // Obsłuż sytuację, gdy weaponTypeId nie istnieje
            throw new NotFoundException("Weapon type not found");
        }

    }

    @Override
    public void deleteWeapon(UUID weaponTypeId, UUID weaponId) {
        service.find(weaponTypeId, weaponId).ifPresentOrElse(
                entity -> service.delete(weaponId),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchWeapon(UUID weaponTypeId, UUID weaponId, PatchWeaponRequest request) {
        service.find(weaponTypeId, weaponId).ifPresentOrElse(
                entity -> service.update(factory.updateWeapon().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public GetWeaponsResponse getPlayerWeapons(UUID id) {
        return service.findAllByPlayer(id)
                .map(factory.weaponsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetWeaponsResponse getWeaponTypeWeapons(UUID id) {
        return service.findAllByWeaponType(id)
                .map(factory.weaponsToResponse())
                .orElseThrow(NotFoundException::new);
    }
}
