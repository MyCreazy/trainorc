package com.ztx.trainorc.service.impl;

import com.alibaba.fastjson.JSON;
import com.ztx.trainorc.Utils.JavaHttpRequest;
import com.ztx.trainorc.api.ORC;
import com.ztx.trainorc.model.vo.*;
import com.ztx.trainorc.service.RecognizeSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 查询需要的值
     *
     * @param wordResultList
     * @param specialStr
     * @return
     */
    private String findSpecialValue(List<WordsResult> wordResultList, String specialStr) {
        String result = "";
        Iterator it = wordResultList.iterator();
        while (it.hasNext()) {
            WordsResult temp = (WordsResult) it.next();
            String[] tempArray = specialStr.split("\\,");
            if (tempArray != null && tempArray.length > 0) {

                boolean isneed = true;
                for (String tmp : tempArray) {
                    if (!temp.getWords().contains(tmp)) {
                        isneed = false;
                        break;
                    }
                }

                if (isneed) {
                    result = temp.getWords();
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 获取匹配结果
     *
     * @param regEx
     * @param str
     * @return
     */
    private List<String> getMatchResult(String regEx, String str) {
        List<String> result = new ArrayList<>();
        // 忽略大小写的写法
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        while (matcher.find()) {
            String temp = "";
            int count = matcher.groupCount();
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    temp += matcher.group(i);
                } else {
                    temp += matcher.group(i) + ",";
                }
            }

            result.add(temp);
        }

        return result;
    }

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
        MRegnizeResponse_VO responseVo = new MRegnizeResponse_VO();
        MTicketContent_VO ticketInfo = new MTicketContent_VO();
        responseVo.setTicketInfo(ticketInfo);
        try {
            String jsonStr = orc.getRecognizeResult(picInfo);
            System.out.println("识别的json字符串:" + jsonStr);
            JsonRootBean jsonobj = JSON.parseObject(jsonStr, JsonRootBean.class);
            ////解析对象，转换为所需对象
            List<WordsResult> wordResultList = jsonobj.getWords_result();

            boolean ismatchSuccess = true;
            if (wordResultList != null && wordResultList.size() > 0) {
                //匹配始发站
                String stattionAndTrainNumStr = this.findSpecialValue(wordResultList, "站");
                if (stattionAndTrainNumStr == null || "".equals(stattionAndTrainNumStr)) {
                    ismatchSuccess = false;
                } else {
                    String trainNumber = stattionAndTrainNumStr.replaceAll("[^(A-Za-z0-9)]", "");
                    if (trainNumber != null && !"".equals(trainNumber)) {
                        ticketInfo.setTrainNumber(trainNumber);
                    }

                    String stationStr = stattionAndTrainNumStr.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
                    String[] stationList = stationStr.split("\\站");
                    if (stationList != null && stationList.length > 1) {
                        ticketInfo.setStartStation(stationList[0]);
                        ticketInfo.setArriveStation(stationList[1]);
                    }
                }

                //匹配出发日期和座位号
                String startDateAndSeat = this.findSpecialValue(wordResultList, "年,月,日,开");
                if (startDateAndSeat == null || "".equals(startDateAndSeat)) {
                    ismatchSuccess = false;
                } else {
                    String[] arrayDateAndSeat = startDateAndSeat.split("\\开");
                    if (arrayDateAndSeat != null && arrayDateAndSeat.length > 0) {
                        String dataStr = arrayDateAndSeat[0].replace("年", "-").replace("月", "-").replace("日", " ");
                        ticketInfo.setStartDate(dataStr);
                        if (arrayDateAndSeat.length == 2) {
                            ticketInfo.setSeatNumber(arrayDateAndSeat[1]);
                        }
                    }
                }

                //匹配检票口
                String checkPassport = this.findSpecialValue(wordResultList, "检票");
                if(checkPassport!=null&&!"".equals(checkPassport))
                {
                    String[] checkArray= checkPassport.split("\\:");
                    if(checkArray!=null&&checkArray.length>1)
                    {
                        ticketInfo.setCheckTicket(checkArray[1]);
                    }
                }

                //有可能座位号分开，这里再判断一下座位号
                String seatStr = this.findSpecialValue(wordResultList, "车,号");
                if (seatStr != null && !"".equals(seatStr) && !startDateAndSeat.equals(seatStr)) {
                    ////说明不是同一行
                    ticketInfo.setSeatNumber(seatStr);
                }
            }

            if (ismatchSuccess) {
                responseVo.setResult(EnumResultFlag.success);
                responseVo.setResultDes("识别成功");
            } else {
                responseVo.setResult(EnumResultFlag.fail);
                responseVo.setResultDes("识别失败");
            }
        }
        catch (Exception ex)
        {
            ////发生异常
            responseVo.setResultDes("识别失败,发生异常");
        }

        result = JSON.toJSONString(responseVo);
        return result;
    }
}
