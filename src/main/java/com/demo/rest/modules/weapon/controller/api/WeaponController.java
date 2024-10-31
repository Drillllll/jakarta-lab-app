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
    @Path("weapontypes/weapons")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponsResponse getWeapons();

    @GET
    @Path("weapontypes/{weapontypeid}/weapons/{weaponid}")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponResponse getWeapon(@PathParam("weapontypeid") UUID weaponTypeId, @PathParam("weaponid") UUID weaponId);

    @PUT
    @Path("weapontypes/{weapontypeid}/weapons/{weaponid}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putWeapon(@PathParam("weapontypeid") UUID weaponTypeId, @PathParam("weaponid") UUID weaponId, PutWeaponRequest request);

    @DELETE
    @Path("weapontypes/{weapontypeid}/weapons/{weaponid}")
    void deleteWeapon(@PathParam("weapontypeid") UUID weaponTypeId, @PathParam("weaponid") UUID weaponId);

    @PATCH
    @Path("weapontypes/{weapontypeid}/weapons/{weaponid}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchWeapon(@PathParam("weapontypeid") UUID weaponTypeId, @PathParam("weaponid") UUID weaponId, PatchWeaponRequest request);

    @GET
    @Path("/players/{id}/weapons")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponsResponse getPlayerWeapons(@PathParam("id") UUID id);

    @GET
    @Path("/weapontypes/{id}/weapons")
    @Produces(MediaType.APPLICATION_JSON)
    GetWeaponsResponse getWeaponTypeWeapons(@PathParam("id") UUID id);
}
