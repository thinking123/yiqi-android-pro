package com.eshop.mvp.http.entity.order;

import java.util.List;

public class RefundInfo {
    /**
     * id : 0
     * orderId : string
     * refundReason : string
     * totalPrice : string
     * voucher : ["string"]
     */

    private int id;
    private String orderId;
    private String refundReason;
    private String totalPrice;
    private List<String> voucher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<String> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<String> voucher) {
        this.voucher = voucher;
    }
}
