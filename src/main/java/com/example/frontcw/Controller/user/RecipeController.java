package com.example.frontcw.Controller.user;

import com.example.frontcw.HelloApplication;
import com.example.frontcw.Json.RecipeModels;
import com.example.frontcw.Json.ReviewModels;
import com.example.frontcw.Json.Token;
import com.example.frontcw.TableViewModels.Reviews;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import okhttp3.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeController {
    @FXML
    public ImageView image;
    @FXML
    public Label stepsLabel;
    @FXML
    public Label ingredientsLabel;
    @FXML
    public Label recipeLabel;
    @FXML
    public Label averageRatingLabel;
    @FXML
    public TextField textTextField;
    @FXML
    public TextField ratingTextField;
    @FXML
    public TableView reviewsTable;
    private Token token;
    private long recipeId;

    @FXML
    private void initialize() throws IOException {
        recipeId = RecipesController.getRecipeId();
        recipeLabel.setText("Recipe №"+recipeId);
        token = HelloApplication.getAnyLoginController().getToken();

        try {
            String url = "http://localhost:8080/api/user/recipes/get/" + recipeId;
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();

            Call call = client.newCall(request);
            Response response = call.execute();
            RecipeModels recipe = new Gson().fromJson(response.body().string(), RecipeModels.class);
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(recipe.getRecipePicture()));
                ImageIO.write(img, "png", new File("temp"));
                Image temp = new Image(new FileInputStream("temp" +".png"));
                image.setImage(temp);
                File myObj = new File("temp.png");
                myObj.delete();
            } catch (Exception e) {
                image.setImage(new Image(new FileInputStream("src/main/resources/static/no-image.png")));
            }
            stepsLabel.setText(recipe.getSteps());
            ingredientsLabel.setText(recipe.getIngredients());

            url = "http://localhost:8080/api/user/reviews/get/recipe/" + recipeId;
            request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();
            call = client.newCall(request);
            response = call.execute();
            List<ReviewModels> reviewsNotSimple = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<ReviewModels>>() {
                    }.getType());
            List<Reviews> reviews = new ArrayList<>();
            int i = 0;
            long sum = 0;
            for (ReviewModels review : reviewsNotSimple) {
                sum = sum + review.getRating();
                i++;
                reviews.add(new Reviews(review.getId(), review.getText(), review.getRating()));
            }
            double temp = (sum/(double) i);
            averageRatingLabel.setText(String.format("%.2f", temp));

            ObservableList<Reviews> observableListReviews = FXCollections.observableArrayList(reviews);

            TableColumn tableViewIdColumn = new TableColumn<>("id");
            TableColumn tableViewTextColumn = new TableColumn<>("Text");
            TableColumn tableViewRatingColumn = new TableColumn<>("Rating");

            reviewsTable.getColumns().addAll(tableViewIdColumn, tableViewTextColumn, tableViewRatingColumn);

            tableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Reviews, Long>("id"));
            tableViewTextColumn.setCellValueFactory(new PropertyValueFactory<Reviews, String>("text"));
            tableViewRatingColumn.setCellValueFactory(new PropertyValueFactory<Reviews, Integer>("rating"));

            reviewsTable.setItems(observableListReviews);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void submitActionButton(ActionEvent actionEvent) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "http://localhost:8080/api/me/reviews/post";
        String json = "{" +
                "\"rating\": " + ratingTextField.getText() + "," +
                "\"text\": " + "\"" + textTextField.getText() + "\"," +
                "\"recipeId\": " + recipeId  +
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
        reviewsTable.getColumns().clear();
        reviewsTable.getItems().clear();
        initialize();
    }

    public void backButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user/recipes_page.fxml"));
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
