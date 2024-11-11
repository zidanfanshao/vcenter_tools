package com.example.vcentertools.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import com.example.vcentertools.model.VulnerabilityScanner;
import com.example.vcentertools.model.CommandExecutor;
import com.example.vcentertools.model.FileUploader;

public class HelloController {

    @FXML private TextField urlField;
    @FXML private Button checkButton;
    @FXML private TextArea resultArea;
    @FXML private ChoiceBox<String> choiceBox;

    private VulnerabilityScanner scanner;

    @FXML
    private void initialize() {
        scanner = new VulnerabilityScanner();

        choiceBox.getItems().add("ALL");
        choiceBox.getItems().add("CVE-2021-22005");
        choiceBox.getItems().add("CVE-2021-21972");
        choiceBox.getItems().add("CVE-2021-21985");
    }

    @FXML
    private void handleCheckButtonClick() {
        String targetUrl = urlField.getText();
        String selectedVulnerability = choiceBox.getValue();

        if (targetUrl == null || targetUrl.isEmpty()) {
            resultArea.setText(" [+] 请输入目标 URL！");
            return;
        }

        if (selectedVulnerability == null || selectedVulnerability.isEmpty()) {
            resultArea.setText(" [+] 请选择漏洞类型！");
            return;
        }

        String result = scanner.scanForVulnerabilities(targetUrl, selectedVulnerability);

        resultArea.setText(result);
    }
}
