package com.ztx.trainorc.api;

import com.ztx.trainorc.Utils.ParamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图像识别结果类
 */
@Component
public class ORC {
    /**
     * 授权服务
     */
    @Autowired
    private   AuthService authService;

    /**
     * 参数管理对象
     */
    @Autowired
    private ParamManager paramManager;
    /**
     * 获取识别结果
     * @param picStr  Base64编码过的字节数组字符串
     * @return
     */
    public String getRecognizeResult(String picStr) {
        String result = "";
        picStr = picStr.replaceAll("\r\n","");
        picStr = picStr.replaceAll("\\+","%2B");
        ////这里判断一下token是否过期，是否不可用
        String tokenStr=authService.getAuth(false);
        String httpUrl = paramManager.getApiUrl()+tokenStr;
        String httpArg =paramManager.getRequestConfig() +picStr;
        String jsonResult = this.requestRecognize(httpUrl, httpArg);
        return jsonResult;
    }

    private String requestRecognize(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", paramManager.getApiKey());
            connection.setRequestProperty("access_token", authService.getAuth(false));
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param imageFile
     * @return 返回Base64编码过的字节数组字符串
     */
    public String encodeImgageToBase64(File imageFile) {
        // 其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
