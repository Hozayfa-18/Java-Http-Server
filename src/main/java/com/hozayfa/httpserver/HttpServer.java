package com.hozayfa.httpserver;

import com.hozayfa.httpserver.config.Configuration;
import com.hozayfa.httpserver.config.ConfigurationManager;
import com.hozayfa.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("C:/Users/hozay/JaspersoftWorkspace/MyReports/einkaufsliste_report.jrxml");
            List<Item> employees = getItems();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Employee Report");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "EinkaufslisteReport.pdf");

            System.out.println("Report generated!");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getItems() {
        return List.of(
                new Item("Bannana", 2, 6),
                new Item("Orange", 1, 5),
                new Item("Chips", 2, 2)
        );
    }
}
