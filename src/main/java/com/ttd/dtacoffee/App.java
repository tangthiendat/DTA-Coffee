package com.ttd.dtacoffee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/appView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DTA Coffee Mangagement System");
        //Add icon
        Image icon256 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/official-logo-256.png")));
        stage.getIcons().add(icon256);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}