package com.example.frontcw.Controller.any;

import com.example.frontcw.HelloApplication;
import com.example.frontcw.Json.AccountModels;
import com.example.frontcw.Json.Token;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;

public class AnyLoginController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    private static Token token;
    public static Token getToken() {
        return token;
    }

    public void onLoginButtonAction(ActionEvent actionEvent) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "http://localhost:8080/api/auth/authenticate";
        String json = "{" +
                "\"login\":" + "\"" + loginField.getText() + "\","+
                "\"password\":" + "\"" + passwordField.getText() + "\"" +
                "}";
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-type", "application/json")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            String responseToken = response.body().string();
            if (response.code() != 200){
                throw new IOException();
            }
            this.token = new Gson().fromJson(responseToken, Token.class);

            request = new Request.Builder()
                    .url("http://localhost:8080/api/me/account/get")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token.getToken())
                    .get()
                    .build();
            call = client.newCall(request);
            response = call.execute();
            AccountModels account = new Gson().fromJson(response.body().string(), AccountModels.class);
            if (account.getRole().equals("USER")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user/recipes_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("recipes_page");
                stage.setScene(scene);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            else if (account.getRole().equals("MODERATOR")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("moderator/recipes_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("recipes_page");
                stage.setScene(scene);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            else if (account.getRole().equals("ADMIN")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/recipes_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("recipes_page");
                stage.setScene(scene);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRegisterButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/register_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("register_page");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
