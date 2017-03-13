package com.wxmimperio.hbase.pojo;

/**
 * Created by weiximing.imperio on 2017/2/23.
 */
public class Bslog {
    /*
        {
            "messageType":101,
            "orderId":"D2010127026168170223113137000001",
            "cardNum":"WN4300001417642",
            "settleTime":"2017-02-23 11:31:37",
            "ptId":"zr00641068998.pt",
            "sndaId":"3446002697",
            "appId":0,
            "areaId":0,
            "appIdPlayer":991002359,
            "areaIdPlayer":0,
            "payTypeId":52,
            "amount":1000,
            "price":10.0,
            "dealerPrice":9.8,
            "depositType":"mp",
            "messageId":"BS3410148782069702400001",
            "messageSourceIp":"10.129.34.10",
            "messageTimestamp":"2017-02-23 11:31:37.024"
        }
    */

/*
    {"messageType":108,
    "orderId":"99000000030655170223134847257400",
    "contextId":"99000000030655170223134847257400",
    "appCode":1,
    "settleTime":"2017-02-23 13:48:49",
    "endpointIp":"14.108.236.52",
    "ptId":"of00674741758.pt",
    "sndaId":"3479676050",
    "appId":991002359,
    "areaId":4,
    "payTypeId":2,
    "amount":1800,
    "balanceBefore":126990,
    "itemInfo":"0","messageId":"BS3436148782892913700001","messageSourceIp":"10.129.34.36","messageTimestamp":"2017-02-23 13:48:49.137"}
*/
    private int messageType;
    private String orderId;
    private String cardNum;
    private String settleTime;
    private String ptId;
    private String sndaId;
    private int appId;
    private int areaId;
    private int appIdPlayer;
    private int areaIdPlayer;
    private int payTypeId;
    private double amount;
    private double price;
    private double dealerPrice;
    private String depositType;
    private String messageId;
    private String messageSourceIp;
    private String messageTimestamp;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getSndaId() {
        return sndaId;
    }

    public void setSndaId(String sndaId) {
        this.sndaId = sndaId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getAppIdPlayer() {
        return appIdPlayer;
    }

    public void setAppIdPlayer(int appIdPlayer) {
        this.appIdPlayer = appIdPlayer;
    }

    public int getAreaIdPlayer() {
        return areaIdPlayer;
    }

    public void setAreaIdPlayer(int areaIdPlayer) {
        this.areaIdPlayer = areaIdPlayer;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDealerPrice() {
        return dealerPrice;
    }

    public void setDealerPrice(double dealerPrice) {
        this.dealerPrice = dealerPrice;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageSourceIp() {
        return messageSourceIp;
    }

    public void setMessageSourceIp(String messageSourceIp) {
        this.messageSourceIp = messageSourceIp;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(String messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    @Override
    public String toString() {
        return "Bslog{" +
                "messageType=" + messageType +
                ", orderId='" + orderId + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", settleTime='" + settleTime + '\'' +
                ", ptId='" + ptId + '\'' +
                ", sndaId='" + sndaId + '\'' +
                ", appId=" + appId +
                ", areaId=" + areaId +
                ", appIdPlayer=" + appIdPlayer +
                ", areaIdPlayer=" + areaIdPlayer +
                ", payTypeId=" + payTypeId +
                ", amount=" + amount +
                ", price=" + price +
                ", dealerPrice=" + dealerPrice +
                ", depositType='" + depositType + '\'' +
                ", messageId='" + messageId + '\'' +
                ", messageSourceIp='" + messageSourceIp + '\'' +
                ", messageTimestamp='" + messageTimestamp + '\'' +
                '}';
    }
}
