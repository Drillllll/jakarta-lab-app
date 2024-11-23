package com.demo.rest.modules.player.controller.implementation;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.player.controller.api.PlayerController;
import com.demo.rest.modules.player.dto.GetPlayerResponse;
import com.demo.rest.modules.player.dto.GetPlayersResponse;
import com.demo.rest.modules.player.dto.PatchPlayerRequest;
import com.demo.rest.modules.player.dto.PutPlayerRequest;
import com.demo.rest.modules.player.service.PlayerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public class PlayerControllerRest implements PlayerController {

    private PlayerService service;
    private final DtoFunctionFactory factory;

    @Inject
    public PlayerControllerRest(DtoFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(PlayerService service) {
        this.service = service;
    }

    @Override
    public GetPlayersResponse getPlayers() {
        return factory.playersToResponse().apply(service.findAll());
    }

    @Override
    public GetPlayerResponse getPlayer(UUID id) {
        return service.find(id)
                .map(factory.playerToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putPlayer(UUID id, PutPlayerRequest request) {
        try {
            service.create(factory.requestToPlayer().apply(id, request));
        } catch (EJBException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deletePlayer(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getPicture(UUID id) {

        // [pictures-in-files] get rid of
        return service.find(id)
                .map(player -> service.getPicture(id))
                .orElseThrow(NotFoundException::new);

        // [pictures-in-files] restore
        /*return service.find(id)
                .map(Player::getPicture)
                .orElseThrow(NotFoundException::new);*/
    }

    @Override
    public void putPicture(UUID id, InputStream picture) {
        service.find(id).ifPresentOrElse(
                entity -> service.updatePicture(id, picture),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deletePicture(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.deletePicture(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchPlayer(UUID id, PatchPlayerRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updatePlayer().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }



}
