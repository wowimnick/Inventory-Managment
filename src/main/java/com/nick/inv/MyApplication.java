package com.nick.inv;

import com.nick.inv.defs.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Inventory.addPart(new InHouse(1,"Discomboblator", 15.00, 2,1,15, 5555));
        Inventory.addPart(new OutSourced(2,"Dinglebooper", 17.23, 14, 1,20,"Nexus"));

        Inventory.addProduct(new ProductInfo(1,"Doopler",15.69,7,1,10));
        Inventory.addProduct(new ProductInfo(2,"Gingydod",112.00,5,4,6));


        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

