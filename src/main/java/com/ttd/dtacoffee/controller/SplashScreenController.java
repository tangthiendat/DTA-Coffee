package com.ttd.dtacoffee.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane splashScreen;

    private void splash(){
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/appView.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setTitle("DTA Coffee Management System");
                            //Add icon
                            Image icon256 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/official-logo-256.png")));
                            stage.getIcons().add(icon256);
                            stage.setScene(scene);
                            stage.setMaximized(true);
                            stage.show();
                            splashScreen.getScene().getWindow().hide();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splash();
    }
}
