package com.eshop.mvp.http.entity.order;

public class RefundBean {
    /**
     * orderId : 1
     * refundReason : 1
     * voucher : 1
     */

    private String orderId;
    private String refundReason;
    private String voucher;

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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
