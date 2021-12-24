module univ.tln {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.h2database;
    requires commons.dbcp2;
    requires java.management;
    requires guava;

    opens univ.tln to javafx.fxml;
    exports univ.tln;
}