package com.ztx.trainorc.service;

/**
 * 图像识别服务接口层
 */
public interface RecognizeSevice {
    /**
     * 获取图片识别结果
     * @param picInfo
     * @return
     */
    String getRecognizeResult(String picInfo);

    /**
     * 转换图片
     * @param localImagePath
     * @return
     */
    String getImageBase64(String localImagePath);
}
