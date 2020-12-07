module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires static lombok;

    opens org.example to javafx.fxml;
    exports org.example;
}