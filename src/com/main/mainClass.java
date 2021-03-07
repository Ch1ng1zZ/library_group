package com.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class mainClass extends Application {

    @Override
    public void start(Stage login) throws Exception {
        try {

            login.setTitle("Login page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/login/login.fxml"));
            login.getIcons().add(new Image("/com/image/user.png"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            login.setScene(scene);
            login.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
