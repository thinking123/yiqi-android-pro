package com.eshop.mvp.http.entity.order;

public class ExpressInfo {
    /**
     * id : 1
     * expressCompany : 1
     * expressNumber : 1
     * linkPhone : 1
     */

    private String id;
    private String expressCompany;
    private String expressNumber;
    private String linkPhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
}
