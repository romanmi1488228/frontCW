package com.example.frontcw.TableViewModels;

import javafx.beans.property.SimpleLongProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RecipesModerator {
    private static long uid = 0;
    public SimpleLongProperty id;
    public ImageView image;
    public Button buttonShow;
    public Button buttonDelete;

    public RecipesModerator(long id, byte[] image) throws IOException {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
            ImageIO.write(img, "png", new File("imagemod" + uid));
            Image temp = new Image(new FileInputStream("imagemod"+ uid + ".png"));
            this.image = new ImageView(temp);
            uid++;
        } catch (Exception e) {
            this.image = new ImageView(new Image(new FileInputStream("src/main/resources/static/no-image.png")));
        }
        this.id = new SimpleLongProperty(id);
        this.buttonShow = new Button("open");
        this.buttonDelete = new Button("delete");
    }

    public long getId() {
        return id.get();
    }

    public ImageView getImage() {
        return image;
    }

    public Button getButtonShow() {
        return buttonShow;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }
}
