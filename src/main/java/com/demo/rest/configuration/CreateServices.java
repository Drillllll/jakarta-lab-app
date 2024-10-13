package com.demo.rest.configuration;

import com.demo.rest.crypto.Pbkdf2PasswordHash;
import com.demo.rest.datastore.DataStore;
import com.demo.rest.player.repository.api.PlayerRepository;
import com.demo.rest.player.repository.implementation.PlayerRepositoryInMemory;
import com.demo.rest.player.service.PlayerService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

//using annotation does not allow configuring order
//@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        PlayerRepository playerRepository = new PlayerRepositoryInMemory(dataSource);

        // [pictures-in-files] get rid of
        ServletContext context = event.getServletContext();
        String pictureDirPath = context.getInitParameter("pictureDirectory");

        event.getServletContext().setAttribute("playerService", new PlayerService(playerRepository, new Pbkdf2PasswordHash(), pictureDirPath));

    }

}
