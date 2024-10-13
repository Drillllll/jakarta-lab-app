package com.demo.rest.configuration;

import com.demo.rest.helpers.DtoFunctionFactory;
import com.demo.rest.modules.player.controller.implementation.PlayerControllerBasic;
import com.demo.rest.modules.player.service.PlayerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

//using annotation does not allow configuring order
//@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        PlayerService playerService = (PlayerService) event.getServletContext().getAttribute("playerService");

        event.getServletContext().setAttribute("playerController", new PlayerControllerBasic(
                playerService,
                new DtoFunctionFactory()
        ));


    }
}
