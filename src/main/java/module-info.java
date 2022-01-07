module univ.tln {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.h2database;
    requires commons.dbcp2;
    requires java.management;
    requires guava;
    // requires guava;

    opens univ.tln to javafx.fxml, javafx.base;
    opens univ.tln.entities.utilisateurs to javafx.base;
    exports univ.tln;
    opens univ.tln.controller to javafx.base, javafx.fxml;
    exports univ.tln.controller;
}
