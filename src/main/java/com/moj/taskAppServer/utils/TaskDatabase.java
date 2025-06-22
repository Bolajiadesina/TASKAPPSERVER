package com.moj.taskAppServer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class TaskDatabase {

    @Value("${spring.datasource.host}")
    private  String host;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;

    @Value("${spring.datasource.instance}")
    private  String instance;

    @Value("${spring.datasource.port}")
    private  String port;

    
    public  Connection connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, Exception {
	        Connection conn = null;
	       
	        //ubslive
	        Class.forName("org.postgresql.Driver").newInstance();
	        conn = DriverManager.getConnection(
	                "jdbc:postgresql://" + host + ":" + port + "/" + instance + "", username, password);
	        System.out.println("Connected to the database");
	        return conn;
	    }


}
