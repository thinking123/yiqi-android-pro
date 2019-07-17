package com.eshop.mvp.http.entity.store;

import com.eshop.mvp.http.entity.home.HomeGoodBean;

import java.util.ArrayList;
import java.util.List;

public class MockAuditGoods {
    public static List<AuditGoods> goodsList;

    public static void init(){
        goodsList = new ArrayList<>();

        AuditGoods b1 = new AuditGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.title = "商品";
        b1.unitPrice = "12.0";

        goodsList.add(b1);

        b1 = new AuditGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "商品";
        b1.unitPrice = "12.0";

        goodsList.add(b1);

        b1 = new AuditGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/c7f43de3-15de-4c79-8e46-8c401c66391f.jpg";
        b1.title = "商品";
        b1.unitPrice = "12.0";

        goodsList.add(b1);


        goodsList.add(b1);
    }


}