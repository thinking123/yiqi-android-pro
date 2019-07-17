package com.eshop.mvp.http.entity.store;

import java.util.List;

public class test {
    /**
     * title : {"name":"来米汇","time":"2016-12-16","info":{"code":"0","message":"成功"}}
     * content : {"detail":[{"storename":"西二旗店","itemlist":[{"id":"1","productname":"苹果"},{"id":"2","productname":"香蕉"},{"id":"3","productname":"橙子"}]},{"storename":"霍营店","itemlist":[{"id":"1","productname":"葡萄"},{"id":"2","productname":"火龙果"},{"id":"3","productname":"榴莲"}]},{"storename":"回龙观店","itemlist":[{"id":"1","productname":"橘子"},{"id":"2","productname":"黄瓜"},{"id":"3","productname":"西红柿"}]}]}
     */

    private TitleBean title;
    private ContentBean content;

    public TitleBean getTitle() {
        return title;
    }

    public void setTitle(TitleBean title) {
        this.title = title;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class TitleBean {
        /**
         * name : 来米汇
         * time : 2016-12-16
         * info : {"code":"0","message":"成功"}
         */

        private String name;
        private String time;
        private InfoBean info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * code : 0
             * message : 成功
             */

            private String code;
            private String message;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }

    public static class ContentBean {
        private List<DetailBean> detail;

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * storename : 西二旗店
             * itemlist : [{"id":"1","productname":"苹果"},{"id":"2","productname":"香蕉"},{"id":"3","productname":"橙子"}]
             */

            private String storename;
            private List<ItemlistBean> itemlist;

            public String getStorename() {
                return storename;
            }

            public void setStorename(String storename) {
                this.storename = storename;
            }

            public List<ItemlistBean> getItemlist() {
                return itemlist;
            }

            public void setItemlist(List<ItemlistBean> itemlist) {
                this.itemlist = itemlist;
            }

            public static class ItemlistBean {
                /**
                 * id : 1
                 * productname : 苹果
                 */

                private String id;
                private String productname;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getProductname() {
                    return productname;
                }

                public void setProductname(String productname) {
                    this.productname = productname;
                }
            }
        }
    }
}
