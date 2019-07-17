package com.eshop.mvp.http.entity.order;

public class RefundUpdateBean {
    /**
     * orderId : 1
     * refundReason : 1
     * voucher : 1
     * id : 1
     */

    private String orderId;
    private String refundReason;
    private String voucher;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
