package com.example.frontcw.Controller.user;

import com.example.frontcw.HelloApplication;
import com.example.frontcw.Json.Token;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipesController {
    @FXML
    public TableView table;
    @FXML
    public TextField idTextField;

    private Token token;

    @FXML
    private void initialize() {
        token = HelloApplication.getAnyLoginController().getToken();
        String url = "http://localhost:8080/api/employee/books/get";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();

            Call call = client.newCall(request);
            Response response = call.execute();
            List<com.example.front.json.Book> booksNotSimple = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<com.example.front.json.Book>>(){}.getType());
            List<Book> books = new ArrayList<>();
            for (com.example.front.json.Book book:booksNotSimple) {
                books.add(new Book(book.id, book.name, book.description, book.genre,
                        book.author, book.ageRestriction, book.condition));
            }
            ObservableList<Book> observableListBooks = FXCollections.observableArrayList(books);

    }

    public void logoutButtonAction(ActionEvent actionEvent) {
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

    public void findButtonAction(ActionEvent actionEvent) {
    }
}
