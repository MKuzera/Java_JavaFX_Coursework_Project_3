package com.example.paoimlab3;

import com.example.paoimlab3.logic.LAB2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 540, 700);
        stage.setTitle("Mateusz Kuzera");

      //  LAB2.init(); // inits the values from lab2

        HelloController controller = fxmlLoader.getController();
        controller.initialize();

        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}