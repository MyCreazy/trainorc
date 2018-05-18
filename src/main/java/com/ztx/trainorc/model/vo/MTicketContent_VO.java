package com.ztx.trainorc.model.vo;

/**
 * 火车票信息
 */
public class MTicketContent_VO {
    /**
     * 出发站
     */
    private String startStation="";

    /**
     * 到达站
     */
    private String arriveStation="";

    /**
     * 到达时间
     */
    private  String startDate="";

    /**
     * 火车班次
     */
    private  String trainNumber="";

    /**
     * 座位号
     */
    private  String seatNumber="";

    /**
     * 乘客姓名（可能为空）
     */
    private  String passengerName="";

    /**
     * 身份证号（可能为空）
     */
    private  String idCard="";

    /**
     * 检票口（可能为空）
     */
    private String checkTicket="";

    /**
     * 获取出发站信息
     * @return
     */
    public String getStartStation() {
        return startStation;
    }

    /**
     * 设置出发站信息
     * @param startStation
     */
    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    /**
     * 获取到达站信息
     * @return
     */
    public String getArriveStation() {
        return arriveStation;
    }

    /**
     * 设置到达站信息
     * @param arriveStation
     */
    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    /**
     * 获取出发时间
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 设置出发时间
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取火车车次号
     * @return
     */
    public String getTrainNumber() {
        return trainNumber;
    }

    /**
     * 设置火车车次号
     * @param trainNumber
     */
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    /**
     * 获取座位号
     * @return
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * 设置座位号
     * @param seatNumber
     */
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * 获取乘客姓名
     * @return
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * 设置乘客姓名
     * @param passengerName
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * 获取身份证号
     * @return
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号
     * @param idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取检票口信息
     * @return
     */
    public String getCheckTicket() {
        return checkTicket;
    }

    /**
     * 设置检票口信息
     * @param checkTicket
     */
    public void setCheckTicket(String checkTicket) {
        this.checkTicket = checkTicket;
    }
}
