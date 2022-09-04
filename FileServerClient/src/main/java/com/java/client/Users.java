package com.java.client;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.sql.*;

public class Users {

    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;

    public boolean checkUser(TextArea login, TextArea password)  {
        try {
            connect();
           preparedStatement = connection.prepareStatement("SELECT name FROM users WHERE name == ? AND password == ?;");
            //ResultSet rs = preparedStatement.executeQuery("SELECT name, password FROM users WHERE name == ? AND password == ?;");
            preparedStatement.setString(1, String.valueOf(login));
            preparedStatement.setString(2, String.valueOf(password));

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO", ButtonType.OK);
            alert.showAndWait();
        }
        return true;

    }


    public void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:usres.db");
        //statement = connection.createStatement();
    }


}
