package com.example.frontcw.Controller.admin;

import com.example.frontcw.HelloApplication;
import com.example.frontcw.Json.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class AdminPostRecipeController {
    public TextField userIdTextField;
    public TextField ingredientsTextField;
    public TextField stepsTextFiled;
    private FileChooser fileChooser = new FileChooser();
    private String imageOnUpload;
    private Token token;

    @FXML
    public void initialize() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        token = HelloApplication.getAnyLoginController().getToken();
    }

    public void imageButtonAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("image picker");
        File selectedFile = fileChooser.showOpenDialog(stage);

        try {
            BufferedImage inputImage = ImageIO.read(selectedFile);
            ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
            ImageIO.write(inputImage, "png", pngContent);
            byte[] imageOnUploadByte = pngContent.toByteArray();
            imageOnUpload = Base64.getEncoder().encodeToString(imageOnUploadByte);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void backButtonAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/recipes_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("recipes_page");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postButtonAction(ActionEvent actionEvent) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "http://localhost:8080/api/moderator/recipes/post";
        String json = "{" +
                "\"ingredients\": " + "\"" + ingredientsTextField.getText() + "\"," +
                "\"steps\": " + "\"" + stepsTextFiled.getText() + "\"," +
                "\"picture\": " + "\"" + imageOnUpload + "\"," +
                "\"user_id\": " + userIdTextField.getText()  +
                "}";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token.getToken())
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();

    }
}
