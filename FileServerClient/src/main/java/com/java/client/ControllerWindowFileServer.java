package com.java.client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ControllerWindowFileServer {
    @FXML
    VBox PanelClient1, PanelServer1;

    public void CopyButton(ActionEvent actionEvent) {
        PanelClintController ClPC = (PanelClintController) PanelClient1.getProperties().get("ctrl");
        PanelServerController SePC = (PanelServerController) PanelServer1.getProperties().get("ctrl");

        if (ClPC.getSelectedFilename() == null && SePC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //srcPC выбранная панель  dstPC - панель в которую хотим скопировать файл
        if (ClPC.getSelectedFilename() != null) {
            try {
                PanelClintController srcPC = null;
                PanelServerController dstPC = null;

                srcPC = ClPC;
                dstPC = SePC;

                Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
                Path dstSPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

                Files.copy(srcPath, dstSPath);
                dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Файл с таким именем уже существует", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
        if (SePC.getSelectedFilename() != null) {
            try {
                PanelServerController srcPC = null;
                PanelClintController dstPC = null;

                srcPC = SePC;
                dstPC = ClPC;

                Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
                Path dstSPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

                Files.copy(srcPath, dstSPath);
                dstPC.updateList(Paths.get(dstPC.getCurrentPath()));

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Файл с таким именем уже существует", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
    }

    public void DeleteButton(ActionEvent actionEvent) {

        PanelClintController ClPC = (PanelClintController) PanelClient1.getProperties().get("ctrl");
        PanelServerController SePC = (PanelServerController) PanelServer1.getProperties().get("ctrl");

        try {
            if (ClPC.getSelectedFilename() == null && SePC.getSelectedFilename() == null) {}
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }


        if (ClPC.getSelectedFilename() != null) {
            try {
                PanelClintController srcPC = null;


                srcPC = ClPC;

                Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());

                Files.delete(srcPath);
                srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        if (SePC.getSelectedFilename() != null) {
            try {
                PanelServerController srcPC = null;

                srcPC = SePC;

                Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());

                Files.delete(srcPath);
                srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }


    }
}
