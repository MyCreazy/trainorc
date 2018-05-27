package com.ztx.trainorc.service.impl;

import com.ztx.trainorc.Utils.JavaHttpRequest;
import com.ztx.trainorc.api.ORC;
import com.ztx.trainorc.model.vo.JsonRootBean;
import com.ztx.trainorc.service.RecognizeSevice;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * 图像识别服务实现层
 */
@Service
public class RecognizeServiceImpl implements RecognizeSevice {
    /**
     * orc对象
     */
    @Autowired
    private ORC orc;

    /**
     * java请求对象
     */
    @Autowired
    private JavaHttpRequest javaHttpRequest;

    /**
     * orc功能对象
     */
    @Autowired
    private ORC orcFunction;

    /**
     * 转换图片
     *
     * @param localImagePath
     * @return
     */
    public String getImageBase64(String localImagePath) {
        String result = "";
        File file = new File(localImagePath);
        result = orcFunction.encodeImgageToBase64(file);
        return result;
    }

    /**
     * 获取图片结果
     *
     * @param picInfo
     * @return
     */
    public String getRecognizeResult(String picInfo) {
        String result = "";
        String jsonStr = orc.getRecognizeResult(picInfo);
        //转换为对象
     /*   JSONObject obj =  new JSONObject().fromObject(jsonStr);
        JsonRootBean jb = (JsonRootBean) JSONObject.toBean(obj, JsonRootBean.class);*/
        return result;
    }
}
