package com.mircolove.tomcat;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 2017/4/17.
 */
public class HttpAcessNetUtil {
    public static String postWithParams(String urlString, Map<String, String> params) {
        URL url;
        HttpURLConnection conn = null;
        String content = "";
        String returnString = "";
        try {
            url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            System.err.println("test..." + urlString);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            Set<Map.Entry<String, String>> entries = params.entrySet();
            if (entries != null) {
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if ("".equals(content)) {
                        content = content + "'" + key + "':'" + URLEncoder.encode(value, "UTF-8") + "'";
                    } else {
                        content = content + ",'" + key + "':'" + URLEncoder.encode(value, "UTF-8") + "'";
                    }
                }
            }
            out.writeBytes(content);
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            System.err.println("test..." + code);
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    returnString = returnString + line;
                }
                System.err.println("test..." + returnString);
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return returnString;
    }

}
