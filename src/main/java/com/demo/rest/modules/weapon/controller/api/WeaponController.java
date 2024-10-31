package com.demo.rest.modules.weapon.controller.api;

import com.demo.rest.modules.weapon.dto.GetWeaponResponse;
import com.demo.rest.modules.weapon.dto.GetWeaponsResponse;
import com.demo.rest.modules.weapon.dto.PatchWeaponRequest;
import com.demo.rest.modules.weapon.dto.PutWeaponRequest;

import java.util.UUID;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
public interface WeaponController {

    @GET
    @Path("/weapons")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponsResponse getWeapons();

    @GET
    @Path("/weapons/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponResponse getWeapon(@PathParam("id") UUID id);

    @PUT
    @Path("/weapons/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putWeapon(@PathParam("id") UUID id, PutWeaponRequest request);

    @DELETE
    @Path("/weapons/{id}")
    void deleteWeapon(@PathParam("id") UUID id);

    @PATCH
    @Path("/weapons/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchWeapon(@PathParam("id") UUID id, PatchWeaponRequest request);

    @GET
    @Path("/players/{id}/weapons")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponsResponse getPlayerWeapons(@PathParam("id") UUID id);

    @GET
    @Path("/weapontypes/{id}/weapons")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponsResponse getWeaponTypeWeapons(@PathParam("id") UUID id);
}
