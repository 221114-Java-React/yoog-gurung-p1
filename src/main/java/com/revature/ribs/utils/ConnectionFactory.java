
package com.revature.ribs.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//singleton design pattern
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;

    static{
        try{
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // class to read properties file
    private final Properties props = new Properties();

    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

//    the static method to control access to the singleton instance
    public static ConnectionFactory getInstance(){
        //Ensuring that the instance not being initialized yet
        if(connectionFactory == null ) connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
        if(con == null) throw new RuntimeException("Could not establish connection with the database!");
        return con;
    }

}
