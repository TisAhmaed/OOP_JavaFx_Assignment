module org.example.oop_and_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.oop_and_javafx to javafx.fxml;
    exports org.example.oop_and_javafx;
}