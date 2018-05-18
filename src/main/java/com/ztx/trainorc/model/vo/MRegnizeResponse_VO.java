package com.ztx.trainorc.model.vo;

/**
 * 返回识别结果
 */
public class MRegnizeResponse_VO {
    /**
     * 识别结果标志
     */
    private EnumResultFlag result=EnumResultFlag.success;

    /**
     * 火车票信息
     */
    private  MTicketContent_VO ticketInfo=null;

    /**
     * 获取识别结果
     * @return
     */
    public EnumResultFlag getResult() {
        return result;
    }

    /**
     * 设置识别结果
     * @param result
     */
    public void setResult(EnumResultFlag result) {
        this.result = result;
    }

    /**
     * 获取火车票信息
     * @return
     */
    public MTicketContent_VO getTicketInfo() {
        return ticketInfo;
    }

    /**
     * 设置火车票信息
     * @param ticketInfo
     */
    public void setTicketInfo(MTicketContent_VO ticketInfo) {
        this.ticketInfo = ticketInfo;
    }
}
