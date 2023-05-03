package com.example.frontcw;

import com.example.frontcw.Controller.any.AnyLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class HelloApplication extends Application {
    private static AnyLoginController anyLoginController;
    public static AnyLoginController getAnyLoginController() {
        return anyLoginController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        File directory = new File("src/main/resources/cache");
        FileUtils.cleanDirectory(directory);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/login_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        anyLoginController = fxmlLoader.getController();
        stage.setTitle("login_page");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){

        launch();
    }

}