package com.ztx.trainorc.controller;

import com.ztx.trainorc.service.impl.RecognizeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    /**
     * 获取识别结果
     * @return
     */
    @ApiOperation(value = "火车票识别",notes = "火车票识别")
    @PostMapping("/trainresult")
    public String getRecognizeResult(@RequestBody String picInfo) {
        String result = "识别成功";
        try
        {
            new RecognizeServiceImpl().getRecognizeResult("");
            LOGGER.error("DFDD");
        }
        catch (Exception ex)
        {}

        return result;
    }
}
