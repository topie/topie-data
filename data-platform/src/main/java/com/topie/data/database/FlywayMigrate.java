package com.topie.data.database;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * Created by cgj on 2016/5/12.
 */
public class FlywayMigrate {

    @Value("${jdbc_url}")
    private String jdbcUrl;

    @Value("${jdbc_username}")
    private String userName;

    @Value("${jdbc_password}")
    private String password;

    @Value("${flyway.init}")
    private Boolean init;

    @PostConstruct
    public void init() {
        if (init) {
            Flyway flyway = new Flyway();
            flyway.setDataSource(jdbcUrl, userName, password);
            flyway.migrate();
        }
    }
}
