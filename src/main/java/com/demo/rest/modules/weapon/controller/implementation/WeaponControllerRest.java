package com.demo.rest.modules.weapon.controller.implementation;

import com.demo.rest.modules.player.entity.PlayerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.weapon.controller.api.WeaponController;
import com.demo.rest.modules.weapon.dto.GetWeaponResponse;
import com.demo.rest.modules.weapon.dto.GetWeaponsResponse;
import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;
import com.demo.rest.modules.weapon.service.WeaponService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;

@Path("")
@RolesAllowed(PlayerRoles.PLAYER)//Secure implementation, not the interface
public class WeaponControllerRest implements WeaponController {

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    private WeaponService service;
    private final DtoFunctionFactory factory;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public WeaponControllerRest(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(WeaponService service) {
        this.service = service;
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
            service.createForCallerPrincipal(factory.requestToWeapon().apply(id, request));
            //This can be done with Response builder but requires method different return type.
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(WeaponController.class, "getWeapon")
                    .build(id)
                    .toString());
            //This can be done with Response builder but requires method different return type.
            //Calling HttpServletResponse#setStatus(int) is ignored.
            //Calling HttpServletResponse#sendError(int) causes response headers and body looking like error.
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteWeapon(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        service.delete(id);
                    } catch (EJBAccessException ex) {
                        System.out.println("WARNING" + ex.getMessage() + ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchWeapon(UUID id, PatchWeaponRequest request) {
        try {
            service.find(id).ifPresentOrElse(
                    entity -> {
                        try {
                            service.update(factory.updateWeapon().apply(entity, request));
                        } catch (EJBAccessException ex) {
                            System.out.println("WARNING" + ex.getMessage() + ex);
                            throw new ForbiddenException(ex.getMessage());
                        }
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
        }
        catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new BadRequestException(ex.getCause());
            }
        }

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
