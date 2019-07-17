package com.eshop.mvp.http.entity.store;

public class DelAccountInfo {
    /**
     * bankId : 1
     * storeId : 1
     */

    private String bankId;
    private String storeId;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
