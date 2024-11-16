package com.demo.rest.modules.player.controller.api;

import com.demo.rest.modules.player.dto.GetPlayerResponse;
import com.demo.rest.modules.player.dto.GetPlayersResponse;
import com.demo.rest.modules.player.dto.PatchPlayerRequest;
import com.demo.rest.modules.player.dto.PutPlayerRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface PlayerController {

    @GET
    @Path("/players")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlayersResponse getPlayers();

    @GET
    @Path("/playeers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlayerResponse getPlayer(@PathParam("id") UUID id);

    @PUT
    @Path("/players/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putPlayer(@PathParam("id") UUID id, PutPlayerRequest request);

    @DELETE
    @Path("/players/{id}")
    void deletePlayer(@PathParam("id") UUID id);

    byte[] getPicture(UUID id);

    void putPicture(UUID id, InputStream portrait);

    @DELETE
    @Path("/players/{id}/picture")
    void deletePicture(@PathParam("id") UUID id);

    @PATCH
    @Path("/players/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchPlayer(@PathParam("id") UUID id, PatchPlayerRequest request);


}
