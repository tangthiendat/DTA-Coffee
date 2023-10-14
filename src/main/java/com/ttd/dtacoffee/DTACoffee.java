package com.ttd.dtacoffee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class DTACoffee extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/splashScreenView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        Image icon256 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/official-logo-256.png")));
        stage.getIcons().add(icon256);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}