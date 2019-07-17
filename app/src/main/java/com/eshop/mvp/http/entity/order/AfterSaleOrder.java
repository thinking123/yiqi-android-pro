package com.eshop.mvp.http.entity.order;

import java.util.List;

public class AfterSaleOrder {
    /**
     * afterSaleTabs : [{"id":"2","orederId":"58347379026995595520190318205630","totalNum":"1","totalPrice":"0.01","state":"1","promptMessage":"等待商家处理","storeId":1,"streoName":"一器商城官方旗舰店","goodsList":[{"goodsId":33,"title":null,"goodsPrice":"0.01","imgUrl":null,"goodsAmount":"1","appClassId":0},{"goodsId":33,"title":null,"goodsPrice":"0.01","imgUrl":null,"goodsAmount":"1","appClassId":0}]}]
     * pageUtil : {"total":1,"pages":1,"pageNum":1}
     */

    private PageUtilBean pageUtil;
    private List<AfterSaleTabsBean> afterSaleTabs;

    public PageUtilBean getPageUtil() {
        return pageUtil;
    }

    public void setPageUtil(PageUtilBean pageUtil) {
        this.pageUtil = pageUtil;
    }

    public List<AfterSaleTabsBean> getAfterSaleTabs() {
        return afterSaleTabs;
    }

    public void setAfterSaleTabs(List<AfterSaleTabsBean> afterSaleTabs) {
        this.afterSaleTabs = afterSaleTabs;
    }

    public static class PageUtilBean {
        /**
         * total : 1
         * pages : 1
         * pageNum : 1
         */

        private int total;
        private int pages;
        private int pageNum;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
    }

    public static class AfterSaleTabsBean {
        /**
         * id : 2
         * orederId : 58347379026995595520190318205630
         * totalNum : 1
         * totalPrice : 0.01
         * state : 1
         * promptMessage : 等待商家处理
         * storeId : 1
         * streoName : 一器商城官方旗舰店
         * goodsList : [{"goodsId":33,"title":null,"goodsPrice":"0.01","imgUrl":null,"goodsAmount":"1","appClassId":0},{"goodsId":33,"title":null,"goodsPrice":"0.01","imgUrl":null,"goodsAmount":"1","appClassId":0}]
         */

        private String id;
        private String orederId;
        private String totalNum;
        private String totalPrice;
        private String state;
        private String promptMessage;
        private int storeId;
        private String streoName;
        private List<GoodsListBean> goodsList;


        private int isLogistics;

        public int getIsLogistics(){return isLogistics;}
        public void setIsLogistics(int isLogistics){
            this.isLogistics = isLogistics;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrederId() {
            return orederId;
        }

        public void setOrederId(String orederId) {
            this.orederId = orederId;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPromptMessage() {
            return promptMessage;
        }

        public void setPromptMessage(String promptMessage) {
            this.promptMessage = promptMessage;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getStreoName() {
            return streoName;
        }

        public void setStreoName(String streoName) {
            this.streoName = streoName;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * goodsId : 33
             * title : null
             * goodsPrice : 0.01
             * imgUrl : null
             * goodsAmount : 1
             * appClassId : 0
             */

            private int goodsId;
            private String title;
            private String goodsPrice;
            private String imgUrl;
            private String goodsAmount;
            private int appClassId;

            public AfterSaleTabsBean afterSaleTabsBean;
            public boolean isHead = false;
            public boolean isFoot = false;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getGoodsAmount() {
                return goodsAmount;
            }

            public void setGoodsAmount(String goodsAmount) {
                this.goodsAmount = goodsAmount;
            }

            public int getAppClassId() {
                return appClassId;
            }

            public void setAppClassId(int appClassId) {
                this.appClassId = appClassId;
            }
        }
    }
    /**
     {
     "afterSaleTabs": [{
     "id": "2",
     "orederId": "58347379026995595520190318205630",
     "totalNum": "1",
     "totalPrice": "0.01",
     "state": "1",
     "promptMessage": "等待商家处理",
     "storeId": 1,
     "streoName": "一器商城官方旗舰店",
     "goodsList": [{
     "goodsId": 33,
     "title": null,
     "goodsPrice": "0.01",
     "imgUrl": null,
     "goodsAmount": "1",
     "appClassId": 0
     },
     {
     "goodsId": 33,
     "title": null,
     "goodsPrice": "0.01",
     "imgUrl": null,
     "goodsAmount": "1",
     "appClassId": 0
     }
     ]
     }],
     "pageUtil": {
     "total": 1,
     "pages": 1,
     "pageNum": 1
     }
     }
     */


}
