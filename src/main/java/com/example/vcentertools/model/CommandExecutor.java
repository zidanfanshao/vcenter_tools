package com.example.vcentertools.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandExecutor {

    /**
     * 执行命令并返回结果
     * @param command 执行的命令
     * @return 执行结果
     */
    public String executeCommand(String command) {
        StringBuilder result = new StringBuilder();

        try {
            // 执行命令
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            // 等待命令执行完毕
            process.waitFor();
        } catch (Exception e) {
            result.append("命令执行失败: ").append(e.getMessage());
        }

        return result.toString();
    }
}
