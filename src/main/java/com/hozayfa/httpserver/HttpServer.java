package com.hozayfa.httpserver;

import com.hozayfa.httpserver.config.Configuration;
import com.hozayfa.httpserver.config.ConfigurationManager;
import com.hozayfa.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);


    public static void main(String[] args){
        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguratio();

        LOGGER.info("Using Port: "+ conf.getPort());
        LOGGER.info("Using webroot: "+ conf.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(),conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
