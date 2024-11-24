package com.demo.rest.modules.weapontype.controller.implementation;

import com.demo.rest.modules.player.entity.PlayerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.weapontype.controller.api.WeaponTypeController;
import com.demo.rest.modules.weapontype.dto.GetWeaponTypeResponse;
import com.demo.rest.modules.weapontype.dto.GetWeaponTypesResponse;
import com.demo.rest.modules.weapontype.dto.PatchWeaponTypeRequest;
import com.demo.rest.modules.weapontype.dto.PutWeaponTypeRequest;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import jakarta.ejb.EJBException;
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
public class WeaponTypeControllerRest implements WeaponTypeController {

    private WeaponTypeService service;
    private final DtoFunctionFactory factory;

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public WeaponTypeControllerRest(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(WeaponTypeService service) {
        this.service = service;
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
    @SneakyThrows
    public void putWeaponType(UUID id, PutWeaponTypeRequest request) {
        try {
            service.create(factory.requestToWeaponType().apply(id, request));
            //This can be done with Response builder but requires method different return type.
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(WeaponTypeController.class, "getWeaponType")
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

    @RolesAllowed(PlayerRoles.ADMIN)//Secure implementation, not the interface
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
