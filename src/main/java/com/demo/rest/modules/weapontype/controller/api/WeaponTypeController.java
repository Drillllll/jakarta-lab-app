package com.demo.rest.modules.weapontype.controller.api;

import com.demo.rest.modules.weapontype.dto.GetWeaponTypeResponse;
import com.demo.rest.modules.weapontype.dto.GetWeaponTypesResponse;
import com.demo.rest.modules.weapontype.dto.PatchWeaponTypeRequest;
import com.demo.rest.modules.weapontype.dto.PutWeaponTypeRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface WeaponTypeController {

    @GET
    @Path("/weapontypes")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponTypesResponse getWeaponTypes();

    @GET
    @Path("/weapontypes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponTypeResponse getWeaponType(@PathParam("id")UUID id);

    @PUT
    @Path("/weapontypes/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putWeaponType(@PathParam("id") UUID id, PutWeaponTypeRequest request);

    @DELETE
    @Path("/weapontypes/{id}")
    void deleteWeaponType(@PathParam("id") UUID id);

    @PATCH
    @Path("/weapontypes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchWeaponType(@PathParam("id") UUID id, PatchWeaponTypeRequest request);
}
