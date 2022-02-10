module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nick.inv to javafx.fxml;
    exports com.nick.inv;
    exports com.nick.inv.defs;
    opens com.nick.inv.defs to javafx.fxml;
}