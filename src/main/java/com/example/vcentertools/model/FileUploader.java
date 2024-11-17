package com.example.vcentertools.model;

import com.example.vcentertools.model.exp.Cve202222005;

public class FileUploader {

    private Cve202222005 cve202222005;

    /**
     * @param targetUrl
     * @param vulnerability
     * @param filename
     * @param filetext
     * @return 执行结果
     */

    public String uploadForVulnerabilities(String targetUrl, String vulnerability,String filename,String filetext) {
        cve202222005 = new Cve202222005();
        StringBuilder result = new StringBuilder();

        try {
            if ("ALL".equals(vulnerability)) {
                result.append(" [-] 请选择具体漏洞").append("\n");
            }else if ("CVE-2021-22005".equals(vulnerability)) {
                result.append(cve202222005.upload_22005(targetUrl,filename,filetext)).append("\n");
            } else if ("CVE-2021-21972".equals(vulnerability)) {
                result.append(" [-] 暂未开发").append("\n");
            } else if ("CVE-2021-21985".equals(vulnerability)) {
                result.append(" [-] 暂未开发").append("\n");
            } else if ("CVE-2022-22954".equals(vulnerability)) {
                result.append(" [-] 暂未开发").append("\n");
            }else {
                result.append(" [-] 未知漏洞类型\n");
            }
        } catch (Exception e) {
            result.append(" [-] 检测过程中发生异常: ").append(e.getMessage()).append("\n");
        }
        return result.toString();
    }
}
