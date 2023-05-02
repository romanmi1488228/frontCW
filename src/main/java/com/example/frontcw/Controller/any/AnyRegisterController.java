package com.example.frontcw.Controller.any;

import com.example.frontcw.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;

public class AnyRegisterController {
    @FXML
    public TextField loginTextField;
    @FXML
    public TextField firstNameTextField;
    @FXML
    public TextField lastNameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Label messageLabel;

    public void registerButtonAction(ActionEvent actionEvent) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "http://localhost:8080/api/auth/register";
        String json = "{" +
                "\"firstName\": " + "\"" + firstNameTextField.getText() + "\"," +
                "\"lastName\": " + "\"" + lastNameTextField.getText() + "\"," +
                "\"login\": " + "\"" + loginTextField.getText() + "\"," +
                "\"password\": " + "\"" + passwordTextField.getText() + "\"" +
                "}";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.code()==200) {
            messageLabel.setText("successfully registered");
        } else {
            messageLabel.setText("some error occurred! Try again.");
        }
    }

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/login_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("login_page");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
