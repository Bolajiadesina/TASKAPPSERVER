package com.moj.taskAppServer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskDatabase {

    /*
     * Database connection parameters to form connection strings
    */

    @Value("${spring.datasource.host}")
    private String host;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.instance}")
    private String instance;

    @Value("${spring.datasource.port}")
    private String port;

    /*
     * database connection class for connecting to the Postgres DB
     */
    public Connection connect()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, Exception {
        Connection conn = null;

        
        Class.forName("org.postgresql.Driver").newInstance();
        conn = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port + "/" + instance + "", username, password);
   
        return conn;
    }

}
