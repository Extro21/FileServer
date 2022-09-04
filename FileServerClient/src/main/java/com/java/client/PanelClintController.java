package com.java.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelClintController implements Initializable {

    @FXML
    TableView<FileInfo> tableClient;

    String pathClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //В таблице хранится FileInfo и преобразооваем его в строку(String)
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        //Приходит одна запись о фале(один fileinfo). Описываем как Преобразуем fileinfo в значение данной ячейки
        // Создаем SimpleStringProperty(строковое свойство)
        // param - одна запись в таблице. У fileinfo спросили Тип, у Типа спросили имя (F или D)
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        // указываем базовую длинну в 24 пикселя
        fileTypeColumn.setPrefWidth(24);
        //добавили стробец в таблицу

        //В таблице хранится FileInfo и преобразооваем его в строку(String)
        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Имя");
        //Приходит одна запись о фале(один fileinfo). Описываем как Преобразуем fileinfo в значение данной ячейи
        // Создаем SimpleStringProperty(строковое свойство)
        // param - одна запись в таблице. У fileinfo спросили Имя
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilename()));
        // указываем базовую длинну в 240  пикселя
        fileNameColumn.setPrefWidth(240);

        //добавили стробцы в таблицу
        tableClient.getColumns().addAll(fileTypeColumn, fileNameColumn);
        // tableServer.getColumns().addAll(fileTypeColumn, fileNameColumn);
        //Сортировка файлов в таблице по типу
        tableClient.getSortOrder().add(fileTypeColumn);
        // tableServer.getSortOrder().add(fileTypeColumn);

        //Paths.get() создание путей
        updateList(Paths.get(".", "FileServerClient/user1"));
        // updateList(Paths.get("..") , tableServer);

    }

    public void updateList(Path path) {
        try {
            pathClient = path.normalize().toAbsolutePath().toString();
            tableClient.getItems().clear();
            // Заполнение таблицы
            // Получаем ссылку на список файлов в таблице. Добавляем пачку файлов
            // Files.list  метод который по указанному пути вернет поток путей
            // map(FileInfo:: new) Преобразооваем пачку путей к нашему FileInfo
            //collect(Collectors.toList() и собираем все это в лист и отдаем в таблицу
            // польза stream api Она берет любую папку, вычитывает от туда список файлов, Список файлов преобразует к
            // списку FileInfo и закидывает в таблицу
            tableClient.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            //чтобы табличка отсортировалась по умолчанию
            tableClient.sort();
        } catch (Exception e) {
            //Всплывающее окно
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось открыть!", ButtonType.OK);
            // показать окно и подождать пока пользователь нажмет на кнопку ОК
            alert.showAndWait();
        }

    }

    public String getSelectedFilename(){
        if(!tableClient.isFocused()){
            return null;
        }
        return tableClient.getSelectionModel().getSelectedItem().getFilename();
    }

    public String getCurrentPath(){
        return pathClient;
    }

}
