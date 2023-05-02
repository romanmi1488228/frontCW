module com.example.frontcw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires okhttp3;
    requires com.google.gson;
    requires okio;
    requires annotations;
    requires static lombok;
    requires java.desktop;

    opens com.example.frontcw to javafx.fxml;
    exports com.example.frontcw;

    opens com.example.frontcw.Json to com.google.gson;
    exports com.example.frontcw.Json;

    opens com.example.frontcw.Controller.any to javafx.fxml;
    exports com.example.frontcw.Controller.any;
    opens com.example.frontcw.Controller.user to javafx.fxml;
    exports com.example.frontcw.Controller.user;
    opens com.example.frontcw.Controller.moderator to javafx.fxml;
    exports com.example.frontcw.Controller.moderator;

    opens com.example.frontcw.TableViewModels to java.base;
    exports com.example.frontcw.TableViewModels;
}