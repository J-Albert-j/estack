package com.kaikeba.wx.util;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author JJH
 * @2021/7/11 17:27
 * 说明：
 */
public class TokenUtil {

    private static String token;
    private static long oldTime = 0;

    public static String getToken() {
        long newTime = System.currentTimeMillis();
        if (newTime - oldTime >= 7100000) {
            oldTime = newTime;
            try {
                setToken();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return token;
    }


    private static void setToken() throws Exception {
        String appid = "wx43a553dffe8ac2f5";
        String secret = "e6fd5c61f471d23b31f7a9e3d859a3d3";
        URL url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String text = null;
        while((text = br.readLine())!=null) {
            sb.append(text);
        }
        br.close();
        JSONObject obj = new JSONObject(sb.toString());
        token = obj.getString("access_token");
    }
}
