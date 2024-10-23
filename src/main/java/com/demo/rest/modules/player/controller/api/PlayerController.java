package com.demo.rest.modules.player.controller.api;

import com.demo.rest.modules.player.dto.GetPlayerResponse;
import com.demo.rest.modules.player.dto.GetPlayersResponse;
import com.demo.rest.modules.player.dto.PatchPlayerRequest;
import com.demo.rest.modules.player.dto.PutPlayerRequest;

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

    void patchPlayer(UUID id, PatchPlayerRequest request);


}