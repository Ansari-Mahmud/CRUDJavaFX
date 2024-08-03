module mahmud.com.crudapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens mahmud.com.crudapp to javafx.fxml;
    exports mahmud.com.crudapp;
    exports mahmud.com.crudapp.controller;
    opens mahmud.com.crudapp.controller to javafx.fxml;
    exports mahmud.com.crudapp.model;
    opens mahmud.com.crudapp.model to javafx.fxml;
}