package com.example.vcentertools.model;

import com.example.vcentertools.model.exp.Cve202222954;

public class CommandExecutor {


    private Cve202222954 cve202222954;


    /**
     * @param targetUrl
     * @param vulnerability
     * @param command 执行的命令
     * @return 执行结果
     */
    public String rceForVulnerabilities(String targetUrl, String vulnerability,String command) {
        StringBuilder result = new StringBuilder();
        cve202222954 = new Cve202222954();

        try {
            if ("ALL".equals(vulnerability)) {
                result.append(" [-] 请选择具体漏洞！").append("\n");
            }else if ("CVE-2021-22005".equals(vulnerability)) {
                result.append(" [-] 暂不支持命令执行！").append("\n");
            } else if ("CVE-2021-21972".equals(vulnerability)) {
                result.append(" [-] 暂未开发！").append("\n");
            } else if ("CVE-2021-21985".equals(vulnerability)) {
                result.append(rceforCVE202121985(targetUrl,command)).append("\n");
            } else if ("CVE-2022-22954".equals(vulnerability)) {
                result.append(cve202222954.command_22954(targetUrl,command)).append("\n");
            } else {
                result.append(" [-] 未知漏洞类型！\n");
            }
        } catch (Exception e) {
            result.append(" [-] 检测过程中发生异常: ").append(e.getMessage()).append("\n");
        }
        return result.toString();
    }


    /**
     * CVE-2021-22005 命令执行
     * @param targetUrl 目标 URL
     * @param command 命令
     * @return 漏洞检测结果
     */

    private String rceforCVE202121985(String targetUrl,String command) {
        try {
            return " [-] 暂未开发";
        }catch (Exception e) {
            return " [-]" + e.getMessage();
        }
    }
}
