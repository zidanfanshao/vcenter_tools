package com.example.vcentertools.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    @FXML private TextField urlField1;
    @FXML private TextArea CommandResultArea;

    @FXML private TextArea UploadTextArea;
    @FXML private TextField UploadFileNameField;

    private VulnerabilityScanner scanner;
    private CommandExecutor commander;
    private FileUploader uploader;

    @FXML
    private void initialize() {
        scanner = new VulnerabilityScanner();
        commander = new CommandExecutor();
        uploader = new FileUploader();

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

    @FXML
    private void handleRceButtonClick() {
        String targetUrl = urlField.getText();
        String selectedVulnerability = choiceBox.getValue();
        String command = urlField1.getText();

        if (targetUrl == null || targetUrl.isEmpty()) {
            CommandResultArea.setText(" [+] 请输入目标 URL！");
            return;
        }
        if (command == null || command.isEmpty()) {
            CommandResultArea.setText(" [+] 请输入需要执行的命令！");
            return;
        }
        if (selectedVulnerability == null || selectedVulnerability.isEmpty()) {
            CommandResultArea.setText(" [+] 请选择正确的漏洞！");
            return;
        }
        String result = commander.rceForVulnerabilities(targetUrl, selectedVulnerability,command);
        CommandResultArea.setText(result);
    }

    @FXML
    private void handleUploadButtonClick() {
        String targetUrl = urlField.getText();
        String selectedVulnerability = choiceBox.getValue();
        String filename = UploadFileNameField.getText();
        String filetext = UploadTextArea.getText();

        if (targetUrl == null || targetUrl.isEmpty()) {
            showAlert(AlertType.ERROR,"报错了" ," [-] 请输入目标 URL！");
            return;
        }
        if (selectedVulnerability == null || selectedVulnerability.isEmpty()) {
            showAlert(AlertType.ERROR,"报错了" ," [-] 请选择正确的漏洞！");
            return;
        }
        if(filename == null || filename.isEmpty()) {
            showAlert(AlertType.ERROR,"报错了" ," [-] 请输入文件名！");
            return;
        }
        if (filetext == null || selectedVulnerability.isEmpty()) {
            showAlert(AlertType.ERROR,"报错了" ," [-] 请填写文件内容！");
            return;
        }
        String result = uploader.uploadForVulnerabilities(targetUrl, selectedVulnerability,filename,filetext);
        showAlert(AlertType.INFORMATION, "成功", result);
    }


    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
