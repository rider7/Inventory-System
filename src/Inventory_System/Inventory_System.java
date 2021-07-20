package Inventory_System;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Inventory_System extends Application {
    //Start the application on the Main_Screen.fxml scene
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/Main_Screen.fxml"));
        primaryStage.setTitle("Brandon Rider Inventory System Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}


