package com.java.client;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {


    @FXML
    public TextArea login;
    @FXML
    public TextArea password;
    Users users;
    private Connection connection;

    @FXML
    private Label welcomeText;


    public void toCome(ActionEvent actionEvent) throws SQLException {
     /*   if(users.checkUser(login , password)){*/
        try {
            new WindowFileServer();
            new Network();

        } catch (Exception e) {
            e.printStackTrace();
        }
       /*   } else {
             System.out.println("Jib,rf 2");
         }*/


    }


    public void Exit(ActionEvent actionEvent) {
        Platform.exit();
    }


}