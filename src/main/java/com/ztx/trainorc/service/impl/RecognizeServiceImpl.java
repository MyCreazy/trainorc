package com.ztx.trainorc.service.impl;

import com.ztx.trainorc.Utils.JavaHttpRequest;
import com.ztx.trainorc.service.RecognizeSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图像识别服务实现层
 */
@Service
public class RecognizeServiceImpl implements RecognizeSevice {
    /**
     * java请求对象
     */
    @Autowired
    private JavaHttpRequest javaHttpRequest;

    /**
     * 获取图片结果
     * @param picInfo
     * @return
     */
    public  String getRecognizeResult(String picInfo)
    {
        String result="";
        ////先测试，从本地加载图片
        String url = "https://api.ocr.space/parse/image";
        String fileName = "E:\\vv.jpg";
        Map<String, String> textMap = new HashMap<String, String>();
        //普通参数：可以设置多个input的name，value
        textMap.put("name", "file");
        textMap.put("filename", "vv.jpg");
        //文件：设置file的name，路径
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("upfile", fileName);
        String contentType = "";//image/png
        try {
            new JavaHttpRequest().postRequestOne(url,true);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // new  JavaHttpRequest().postRequest(url, textMap, fileMap,contentType,true);
        return result;
    }
}
