package com.example.frontcw.TableViewModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class ReviewsModerator {
    private SimpleStringProperty text;
    private SimpleLongProperty id;
    private SimpleIntegerProperty rating;
    public Button buttonDelete;

    public ReviewsModerator(long id, String text, int rating) {
        this.id = new SimpleLongProperty(id);
        this.rating = new SimpleIntegerProperty(rating);
        this.text = new SimpleStringProperty(text);
        this.buttonDelete = new Button("delete");
    }

    public long getId() {
        return id.get();
    }

    public int getRating() {
        return rating.get();
    }

    public String getText() {
        return text.get();
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }
}
