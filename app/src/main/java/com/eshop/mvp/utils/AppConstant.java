package com.eshop.mvp.utils;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/31 上午11:25
 * @Package com.eshop.mvp.utils
 **/

public interface AppConstant {


    interface Api {
        String TOKEN = "token";
    }

    interface User {
        String INFO = "userInfo";
        String ID = "id";
    }

    interface ActivityIntent {
        String BEAN = "BEAN";
        String USER_BEAN = "user_bean";
        String PRODUCT_BEAN = "product_bean";
        String PRODUCT_IDS = "PRODUCT_IDS";

        String ORDER_STATUS = "ORDER_STATUS";
        String SEARCH_CONTENT = "SEARCH_CONTENT";
        String IMAGE_URL = "IMAGE_URL";
    }

    interface Image {
        String FILE_PROVIDER = "com.eshop.FileProvider";

    }

    String FIRST_OPEN = "FIRST_OPEN";
}
