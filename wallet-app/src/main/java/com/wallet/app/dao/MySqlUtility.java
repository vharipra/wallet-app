package com.wallet.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlUtility {

    public static Connection getConnectionToMySQL() {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/local","root","1234");

        } catch (SQLException e) {
            System.out.println("DB - Connection Issues!!");
            System.out.println(e.getMessage());
        }

        return connection;
    }

//    public static void main(String[] args) {
//        getConnectionToMySQL();
//    }

}

