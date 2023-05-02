package com.example.frontcw.TableViewModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reviews {
    private SimpleStringProperty text;
    private SimpleLongProperty id;
    private SimpleIntegerProperty rating;

    public Reviews(long id, String text, int rating) {
        this.id = new SimpleLongProperty(id);
        this.rating = new SimpleIntegerProperty(rating);
        this.text = new SimpleStringProperty(text);
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
}
