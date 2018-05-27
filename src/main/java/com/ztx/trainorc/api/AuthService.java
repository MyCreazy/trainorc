package com.ztx.trainorc.api;

import com.ztx.trainorc.Utils.ParamManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 授权服务
 */
@Component
public class AuthService {
    /**
     * token字符串
     */
    private  static  String tokenStr="";

    /**
     * 程序启动就开始计时
     */
   private  static  Date lastTokenTime=new Date();

    /**
     * 参数管理对象
     */
   @Autowired
   private ParamManager paramManager;

    /**
     * 获取两个时间的间隔天数
     * @param date1
     * @param date2
     * @return
     */
    private int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**\
     * 获取token
     * @return
     */
    public  String getAuth(boolean isUpdateToken) {
        ////加锁，实现单例
        synchronized (this) {
            String clientId = paramManager.getApiKey();
            String clientSecret =paramManager.getSecretKey();
            //只有过期了才会重新获取
            if(isUpdateToken||"".equals(tokenStr)) {
                tokenStr = getToken(clientId, clientSecret);
            }
            else
            {
                ////即使不让更新，也根据时间判断一下是否强制更新
              int days=  differentDaysByMillisecond(lastTokenTime,new Date());
              if(days>=paramManager.getTokenexpiresDay())
              {
                  tokenStr = getToken(clientId, clientSecret);
              }
            }

            return tokenStr;
        }
    }

    /**
     *
     * @param ak
     * @param sk
     * @return
     */
    private   String getToken(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
