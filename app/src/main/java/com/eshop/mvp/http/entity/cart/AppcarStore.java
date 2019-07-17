package com.eshop.mvp.http.entity.cart;

import java.util.List;

public class AppcarStore {
    /**
     * addressId : 2
     * orderType : 0
     * storelist : [{"storeId":"1","remarks":"111","carlist":"2, 5"},{"storeId":"3","remarks":"222","carlist":"3，4"}]
     */

    private String addressId;
    private String orderType;
    private List<StorelistBean> storelist;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<StorelistBean> getStorelist() {
        return storelist;
    }

    public void setStorelist(List<StorelistBean> storelist) {
        this.storelist = storelist;
    }

    public static class StorelistBean {
        /**
         * storeId : 1
         * remarks : 111
         * carlist : 2, 5
         */

        private String storeId;
        private String remarks;
        private String carlist;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCarlist() {
            return carlist;
        }

        public void setCarlist(String carlist) {
            this.carlist = carlist;
        }
    }
}




