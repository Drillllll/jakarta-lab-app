package com.demo.rest.modules.player.model.converter;

import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.model.PlayerModel;
import com.demo.rest.modules.player.service.PlayerService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;


import java.util.Optional;

/**
 * Faces converter . The managed attribute in {@link @FacesConverter} allows the converter to be
 * the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not managed by
 * container that is injection was not possible. As this bean is not annotated with scope the beans.xml descriptor must
 * be present.
 */
@FacesConverter(forClass = PlayerModel.class, managed = true)
public class PlayerModelConverter   implements Converter<PlayerModel>{


    private final PlayerService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;



    @Inject
    public PlayerModelConverter(PlayerService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public PlayerModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Player> player = service.find(value);
        return player.map(factory.playerToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PlayerModel value) {
        return value == null ? "" : value.getLogin();
    }

}
