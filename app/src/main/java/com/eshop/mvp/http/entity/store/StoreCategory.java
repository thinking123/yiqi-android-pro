package com.eshop.mvp.http.entity.store;

public class StoreCategory {
    /**
     * categoryName :
     * categoryOrder :
     * storeId :
     */

    private String categoryName;
    private String categoryOrder;
    private String storeId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(String categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
