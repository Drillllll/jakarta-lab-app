package com.demo.rest.configuration;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.helpers.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

//@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datasource", new DataStore(new CloningUtility()));
    }

}
