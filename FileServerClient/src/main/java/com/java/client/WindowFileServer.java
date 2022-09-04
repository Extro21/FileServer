package com.java.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowFileServer {

    public WindowFileServer() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = new Stage();
        stage.setTitle("File Server");
        stage.setScene(scene);
        stage.show();
    }

   /* @FXML
    public void ExitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }*/
}
