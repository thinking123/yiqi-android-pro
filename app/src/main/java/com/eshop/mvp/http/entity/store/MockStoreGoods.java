package com.eshop.mvp.http.entity.store;

import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.product.Store;

import java.util.ArrayList;
import java.util.List;

public class MockStoreGoods {
    public static List<Store> storeList;

    public static void init(){
        storeList = new ArrayList<>();

        Store b1 = new Store();
        b1.storeImg = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.streoName = "商品";

        storeList.add(b1);

        b1 = new Store();
        b1.storeImg = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.streoName = "商品";

        storeList.add(b1);

        b1 = new Store();
        b1.storeImg = "http://zack-image.oss-cn-beijing.aliyuncs.com/c7f43de3-15de-4c79-8e46-8c401c66391f.jpg";
        b1.streoName = "商品";

        storeList.add(b1);


        storeList.add(b1);
    }


}