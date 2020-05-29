package org.china2b2t.captchalib;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class Internet {
    public static UUID getuuid(String name) {
        UUID u = null;
        String su = null;
        String str = null;
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = "";
        try {
            URL httpUrl = null;
            httpUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                response += lines;
            }
            reader.close();
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (response.equals(null)) {
            return null;
        } else if (response.equals("")) {
            return null;
        } else {
            JSONObject jsonObj = JSON.parseObject(response);
            if (jsonObj.get("name").equals(name)) {

                str = jsonObj.getString("id");
                String s1 = str.substring(0, 8);
                String s2 = str.substring(8, 12);
                String s3 = str.substring(12, 16);
                String s4 = str.substring(16, 20);
                String s5 = str.substring(20, 32);

                su = s1+"-"+s2+"-"+s3+"-"+s4+"-"+s5;

                u = UUID.fromString(su);

            }
        }

        return u;
    }
}
