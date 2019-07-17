package com.eshop.mvp.http.entity.order;

public class AfterSales {
    /**
     * id : 1
     * state : 1
     * storeId : 1
     * auditMessage : 1
     * nsigneePhone : 1
     * nsigneeName : 1
     * nsigneeAdress : 1
     */

    private String id;
    private String state;
    private String storeId;
    private String auditMessage;
    private String nsigneePhone;
    private String nsigneeName;
    private String nsigneeAdress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAuditMessage() {
        return auditMessage;
    }

    public void setAuditMessage(String auditMessage) {
        this.auditMessage = auditMessage;
    }

    public String getNsigneePhone() {
        return nsigneePhone;
    }

    public void setNsigneePhone(String nsigneePhone) {
        this.nsigneePhone = nsigneePhone;
    }

    public String getNsigneeName() {
        return nsigneeName;
    }

    public void setNsigneeName(String nsigneeName) {
        this.nsigneeName = nsigneeName;
    }

    public String getNsigneeAdress() {
        return nsigneeAdress;
    }

    public void setNsigneeAdress(String nsigneeAdress) {
        this.nsigneeAdress = nsigneeAdress;
    }
}
