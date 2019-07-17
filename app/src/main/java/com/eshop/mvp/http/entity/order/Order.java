package com.eshop.mvp.http.entity.order;

public class Order {


    /**
     * id : 103
     * userId : 43
     * storeId : 1
     * orderId : 66698102903620706620190318111436
     * createTime : 2019-03-18
     * orderStatus : 0
     * orderType : 0
     * payStatus : 0
     * receiveUserName : 测试
     * address : 广东省 广州市 天河区测试
     * receivePhone : 13928439991
     * remarks : null
     * totalPrice : 0.01
     * storeName : null
     * expressNumber : null
     * expressCompany : null
     * reminderShipment : 1
     * reminderPayment : null
     * goodsAmount : null
     * payType : null
     * payNumber : null
     * remainingTime : 1天
     * payOrderId : null
     * freightTotal : 0
     * goodsTotal : 0.01
     * freightState : 0
     * apporderdetailsList : null
     */

    private int id;
    private int userId;
    private int storeId;
    private String orderId;
    private String createTime;
    private int orderStatus;
    private int orderType;
    private int payStatus;
    private String receiveUserName;
    private String address;
    private String receivePhone;
    private Object remarks;
    private double totalPrice;
    private Object storeName;
    private Object expressNumber;
    private Object expressCompany;
    private int reminderShipment;
    private Object reminderPayment;
    private Object goodsAmount;
    private Object payType;
    private Object payNumber;
    private String remainingTime;
    private Object payOrderId;
    private int freightTotal;
    private double goodsTotal;
    private int freightState;
    private Object apporderdetailsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Object getStoreName() {
        return storeName;
    }

    public void setStoreName(Object storeName) {
        this.storeName = storeName;
    }

    public Object getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(Object expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Object getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(Object expressCompany) {
        this.expressCompany = expressCompany;
    }

    public int getReminderShipment() {
        return reminderShipment;
    }

    public void setReminderShipment(int reminderShipment) {
        this.reminderShipment = reminderShipment;
    }

    public Object getReminderPayment() {
        return reminderPayment;
    }

    public void setReminderPayment(Object reminderPayment) {
        this.reminderPayment = reminderPayment;
    }

    public Object getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Object goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(Object payNumber) {
        this.payNumber = payNumber;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Object getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Object payOrderId) {
        this.payOrderId = payOrderId;
    }

    public int getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(int freightTotal) {
        this.freightTotal = freightTotal;
    }

    public double getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(double goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public int getFreightState() {
        return freightState;
    }

    public void setFreightState(int freightState) {
        this.freightState = freightState;
    }

    public Object getApporderdetailsList() {
        return apporderdetailsList;
    }

    public void setApporderdetailsList(Object apporderdetailsList) {
        this.apporderdetailsList = apporderdetailsList;
    }
}
