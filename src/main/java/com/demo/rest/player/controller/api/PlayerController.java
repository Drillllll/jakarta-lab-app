package com.demo.rest.player.controller.api;

import com.demo.rest.player.dto.GetPlayerResponse;
import com.demo.rest.player.dto.GetPlayersResponse;
import com.demo.rest.player.dto.PutPlayerRequest;

import java.io.InputStream;
import java.util.UUID;

public interface PlayerController {

    GetPlayersResponse getPlayers();

    GetPlayerResponse getPlayer(UUID id);

    void putPlayer(UUID id, PutPlayerRequest request);

    void deletePlayer(UUID id);

    byte[] getPicture(UUID id);

    void putPicture(UUID id, InputStream portrait);

    void deletePicture(UUID id);
}
