package com.eshop.mvp.http.entity.store;

public class StoreCategoryEdit {
    /**
     * categoryName :
     * categoryOrder :
     * storeId :
     */

    private String categoryName;
    private String categoryOrder;
    private String categoryId;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String storeId) {
        this.categoryId = storeId;
    }
}
