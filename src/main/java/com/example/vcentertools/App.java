package com.example.vcentertools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/vcentertools/hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("VCenter 漏洞利用工具v0.4           by:   zidanfanshao  （严禁用于非授权的测试及非法用途）");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
