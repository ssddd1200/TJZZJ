package com.yjs.server;

import com.alibaba.fastjson.JSONObject;
import com.yjs.utils.INIUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

public class RequestServer {

    private String IPAddr;
    private String token;
    public String path1;
    public String path2;
    public String path3;
    public String path4;

    public RequestServer(){

        String ip = INIUtils.getValue("config", "ip");
        String tk = INIUtils.getValue("config", "token");
        if (ip.isEmpty()){
            IPAddr = "127.0.0.1:80";
        }else{
            IPAddr = ip;
        }
        if (tk.isEmpty()){
            token = "yifang_his_open_330921";
        }else{
            token = tk;
        }
        path1 = INIUtils.getValue("config", "path1");
        path2 = INIUtils.getValue("config", "path2");
        path3 = INIUtils.getValue("config", "path3");
        path4 = INIUtils.getValue("config", "path4");

    }

    // 体检报告
    public InputStream getWebPDF(String str) throws IOException {
        String url = "";
        if (IPAddr.indexOf("http") > 0){
            url = IPAddr + path1;
        }else{
            url = "http://" + IPAddr + path1;
        }
        url = url + "?flag=1&xingming=&tijianbh=" + str;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set("token", token);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Resource> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), Resource.class);
        return response.getBody().getInputStream();
    }

    // 体检项目
    public JSONObject getTijianxm(String str){
        String url = "";
        if (IPAddr.indexOf("http") > 0){
            url = IPAddr + path2;
        }else{
            url = "http://" + IPAddr + path2;
        }
        url = url + "?tijianbh=" + str;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", token);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), JSONObject.class);
        return response.getBody();
    }

    // 来检确认
    public JSONObject sureTijian(String str){
        String url = "";
        if (IPAddr.indexOf("http") > 0){
            url = IPAddr + path3;
        }else{
            url = "http://" + IPAddr + path3;
        }
        url = url + "/" + str;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", token);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null, headers), JSONObject.class);
        return response.getBody();
    }

    // 导引单打印
    public InputStream printDaoyind(String str) throws IOException {
        String url = "";
        if (IPAddr.indexOf("http") > 0){
            url = IPAddr + path4;
        }else{
            url = "http://" + IPAddr + path4;
        }
        url = url + "?flag=1&tijianbh=" + str;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set("token", token);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Resource> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), Resource.class);
        return response.getBody().getInputStream();
    }
}
