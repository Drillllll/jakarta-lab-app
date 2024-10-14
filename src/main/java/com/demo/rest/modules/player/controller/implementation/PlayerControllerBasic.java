package com.demo.rest.modules.player.controller.implementation;

import com.demo.rest.controller.servlet.exception.BadRequestException;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.player.controller.api.PlayerController;
import com.demo.rest.modules.player.dto.GetPlayerResponse;
import com.demo.rest.modules.player.dto.GetPlayersResponse;
import com.demo.rest.modules.player.dto.PatchPlayerRequest;
import com.demo.rest.modules.player.dto.PutPlayerRequest;
import com.demo.rest.modules.player.service.PlayerService;

import java.io.InputStream;
import java.util.UUID;

public class PlayerControllerBasic implements PlayerController {

    private final PlayerService service;
    private final DtoFunctionFactory factory;

    public PlayerControllerBasic(PlayerService playerService, DtoFunctionFactory factory) {
        this.service = playerService;
        this.factory = factory;
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
        } catch (IllegalArgumentException ex) {
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
