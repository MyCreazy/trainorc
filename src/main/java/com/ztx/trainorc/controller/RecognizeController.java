package com.ztx.trainorc.controller;

import com.ztx.trainorc.service.RecognizeSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 图像识别控制层
 */
@Api(value = "train_ticket_recognize", description = "train_ticket_recognize")
@RestController
@RequestMapping("/api/orc")
public class RecognizeController {
    /**
     * 获取日志对象
     */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 服务对象
     */
    @Resource
    private RecognizeSevice recognizeSevice;

    /**
     * 获取识别结果
     *
     * @return
     */
    @ApiOperation(value = "火车票识别", notes = "火车票识别")
    @PostMapping("/trainresult")
    public String getRecognizeResult(@RequestBody String picInfo) {
        String result = "";
        LOGGER.error("开始识别");
        result = recognizeSevice.getRecognizeResult(picInfo);
        System.out.println("识别结果:"+result);
        return result;
    }

    /**
     * 获取图片的base64
     *
     * @param localImagePath
     * @return
     */
    @ApiOperation(value = "本地图片转换为base64", notes = "本地图片转换为base64")
    @PostMapping("/ImageBase64")
    public String getImageBase64(@RequestBody String localImagePath) {
        String result = "";
        try {
            result = recognizeSevice.getImageBase64(localImagePath);
        } catch (Exception ex) {
        }
        return result;
    }
}
