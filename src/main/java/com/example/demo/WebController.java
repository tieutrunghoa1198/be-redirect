package com.example.demo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {
    private String API_ENDPOINT = "https://discord.com/api/v10";
    private String CLIENT_ID = "977523393060560967";
    private String CLIENT_SECRET = "WmWit_l8_HyuVKC55hlWSjC6wl6A3SEx";
    private String REDIRECT_URI = "http://localhost:3001/";
    private String GRANT_TYPE = "authorization_code";
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<String> get(@RequestParam(name = "code") String code) throws UnsupportedEncodingException {
        /**
         * client_id = 977523393060560967
         * secret_id = WmWit_l8_HyuVKC55hlWSjC6wl6A3SEx
         */

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded");

        Map<String, String> map= new HashMap<>();
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        map.put("grant_type", GRANT_TYPE);
        map.put("code", code.toString());
        map.put("redirect_uri", REDIRECT_URI.toString());

        HttpEntity<String> request = new HttpEntity<>(getDataString(map), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntityStr =
                restTemplate.postForEntity(API_ENDPOINT + "/oauth2/token", request, String.class);
        System.out.println(responseEntityStr.getBody());
        return responseEntityStr;

    }

    private String getDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
