package com.demo.rest.helpers;

import com.demo.rest.modules.player.dto.function.PlayerToResponseFunction;
import com.demo.rest.modules.player.dto.function.PlayersToResponseFunction;
import com.demo.rest.modules.player.dto.function.RequestToPlayerFunction;

public class DtoFunctionFactory {
    public PlayersToResponseFunction playersToResponse() {
        return new PlayersToResponseFunction();
    }

    public PlayerToResponseFunction playerToResponse() {
        return new PlayerToResponseFunction();
    }

    public RequestToPlayerFunction requestToPlayer() {
        return new RequestToPlayerFunction();
    }
}