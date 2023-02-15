package com.deloop.application.config;

import com.deloop.infrastructure.DBEbeanService;
import com.deloop.infrastructure.DBEbeanServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ebean.datasource.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
//@Import({DBUserRepositoryConfiguration.class})
public class DBConfiguration {

    @Value("${env}")
    private String env;

    @Value("${db.driver}")
    private String db_driver;

    @Value("${db.language}")
    private String db_language;

    @Value("${db.server}")
    private String db_server;

    @Value("${db.port}")
    private short db_port;

    @Value("${db.name}")
    private String db_name;

    @Value("${db.user}")
    private String db_user;

    @Value("${db.password}")
    private String db_password;

    @Value("${db.executeddl}")
    private boolean db_execute_ddl;

    @Bean
        // (name = "apiEbeanService")
    DBEbeanService dbEbeanService(ObjectMapper objectMapper) {

        String databaseServer = System.getProperty("db_server", db_server);
        String databaseName = System.getProperty("db_name", db_name);
        String databaseUser = System.getProperty("db_user", db_user);
        String databasePassword = System.getProperty("db_password", db_password);

        String databaseLanguage = db_language;

        DataSourceConfig dataSource = new DataSourceConfig();
//        dataSource.setDriver(db_driver);
//        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
//        dataSource.setDriver("org.postgresql.Driver");
        dataSource.setUsername(databaseUser);
        dataSource.setPassword(databasePassword);

        String message = "DB Server => " + databaseServer;
        log.warn(message);
        System.err.println(message);

        short databasePort = db_port;
        short connectTimeout = 5000;
        short socketTimeout = 5000;

        String url = String.format("jdbc:%s://%s:%d/%s"
                        + "?characterEncoding=UTF-8&serverTimezone=UTC&autoReconnect=true"
                        + "&useSSL=false&readFromMasterWhenNoSlaves=true"
                        + "&connectTimeout=" + "%d" + "&socketTimeout=" + "%d",
                databaseLanguage, databaseServer, databasePort,
                databaseName, connectTimeout, socketTimeout);

        dataSource.setUrl(url);
        dataSource.setReadOnly(false);
        dataSource.setMaxInactiveTimeSecs(1);
        dataSource.setTrimPoolFreqSecs(10);
        dataSource.setLeakTimeMinutes(1);
        dataSource.setMaxConnections(60);
        dataSource.setWaitTimeoutMillis(5000);

        log.warn(dataSource.getUrl());

        DBEbeanService db = new DBEbeanServiceImpl(dataSource, databaseName, objectMapper);
        return db;
    }
}
