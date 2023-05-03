package com.example.frontcw.Controller.admin;

import com.example.frontcw.HelloApplication;
import com.example.frontcw.Json.RecipeModels;
import com.example.frontcw.Json.Token;
import com.example.frontcw.TableViewModels.Recipes;
import com.example.frontcw.TableViewModels.RecipesModerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminRecipesController {
    @FXML
    public TableView table;
    @FXML
    public TextField idTextField;

    private Token token;

    private static long recipeId;
    public static long getRecipeId(){
        return recipeId;
    }

    @FXML
    private void initialize() throws IOException {
        token = HelloApplication.getAnyLoginController().getToken();
        String url = "http://localhost:8080/api/user/recipes/get";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();

            Call call = client.newCall(request);
            Response response = call.execute();
            List<RecipeModels> recipesNotSimple = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<RecipeModels>>() {
                    }.getType());
            List<RecipesModerator> recipes = new ArrayList<>();
            for (RecipeModels recipe : recipesNotSimple) {
                recipes.add(new RecipesModerator(recipe.getId(), recipe.getRecipePicture()));
            }
            for (RecipesModerator recipe:recipes) {
                recipe.buttonShow.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            recipeId = recipe.id.get();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/recipe_page.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setTitle("recipe_page");
                            stage.setScene(scene);
                            stage.show();
                            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                recipe.buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            String url = "http://localhost:8080/api/moderator/recipes/delete/" + recipe.id.get();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("Authorization", "Bearer " + token)
                                    .delete()
                                    .build();
                            Call call = client.newCall(request);
                            Response response = call.execute();

                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/recipes_page.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setTitle("recipes_page");
                            stage.setScene(scene);
                            stage.show();
                            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            ObservableList<RecipesModerator> observableListRecipes = FXCollections.observableArrayList(recipes);

            TableColumn tableViewIdColumn = new TableColumn<>("id");
            TableColumn tableViewImageColumn = new TableColumn<>("Image");
            TableColumn tableViewButtonShowColumn = new TableColumn<>("Button Show");
            TableColumn tableViewButtonDeleteColumn = new TableColumn<>("Button Delete");

            table.getColumns().addAll(tableViewIdColumn, tableViewImageColumn, tableViewButtonShowColumn, tableViewButtonDeleteColumn);

            tableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Recipes, Long>("id"));
            tableViewImageColumn.setCellValueFactory(new PropertyValueFactory<Recipes, ImageView>("image"));
            tableViewButtonShowColumn.setCellValueFactory(new PropertyValueFactory<Recipes, Button>("buttonShow"));
            tableViewButtonDeleteColumn.setCellValueFactory(new PropertyValueFactory<Recipes, Button>("buttonDelete"));

            table.setItems(observableListRecipes);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findButtonAction(ActionEvent actionEvent) {
        try {
            String url = "http://localhost:8080/api/user/recipes/get/" + idTextField.getText();
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();

            Call call = client.newCall(request);
            Response response = call.execute();
            List<RecipeModels> recipesNotSimple = List.of(new Gson().fromJson(response.body().string(), RecipeModels.class));
            List<RecipesModerator> recipes = new ArrayList<>();
            for (RecipeModels recipe : recipesNotSimple) {
                recipes.add(new RecipesModerator(recipe.getId(), recipe.getRecipePicture()));
            }
            for (RecipesModerator recipe : recipes) {
                recipe.buttonShow.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            recipeId = recipe.id.get();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/recipe_page.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setTitle("recipe_page");
                            stage.setScene(scene);
                            stage.show();
                            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                recipe.buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            String url = "http://localhost:8080/api/recipes/delete/" + recipe.id.get();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("Authorization", "Bearer " + token)
                                    .delete()
                                    .build();
                            Call call = client.newCall(request);
                            Response response = call.execute();

                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/recipes_page.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setTitle("recipes_page");
                            stage.setScene(scene);
                            stage.show();
                            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            ObservableList<RecipesModerator> observableListRecipes = FXCollections.observableArrayList(recipes);
            table.getItems().clear();
            table.setItems(observableListRecipes);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void postRecipeButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/post_recipe_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("post_recipe_page");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
