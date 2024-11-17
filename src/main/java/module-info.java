module com.example.vcentertools {
    requires javafx.controls;
    requires javafx.fxml;
    requires cn.hutool;
    requires java.sql;
    requires java.net.http;


    opens com.example.vcentertools to javafx.fxml;
    exports com.example.vcentertools;
    exports com.example.vcentertools.controller;
    opens com.example.vcentertools.controller to javafx.fxml;
}