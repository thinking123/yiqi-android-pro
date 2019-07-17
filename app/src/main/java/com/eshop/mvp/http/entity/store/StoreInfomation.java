package com.eshop.mvp.http.entity.store;

public class StoreInfomation {
    /**
     * storeId : string
     * storeImg : string
     * storeMoney : string
     * storeName : string
     * storeOrderNum : string
     */

    private String storeId;
    private String storeImg;
    private String storeMoney;
    private String storeName;
    private String storeOrderNum;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getStoreMoney() {
        return storeMoney;
    }

    public void setStoreMoney(String storeMoney) {
        this.storeMoney = storeMoney;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreOrderNum() {
        return storeOrderNum;
    }

    public void setStoreOrderNum(String storeOrderNum) {
        this.storeOrderNum = storeOrderNum;
    }
}
