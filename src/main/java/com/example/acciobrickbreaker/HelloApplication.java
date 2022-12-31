package com.example.acciobrickbreaker;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static Stage globalstage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        globalstage=stage;

        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onHelloButtonClick() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gamescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        globalstage.setScene(scene);
        globalstage.setTitle("Welcome to the gamescreen!");

        globalstage.show();
    }
    public static void main(String[] args) {

        launch();
    }
}