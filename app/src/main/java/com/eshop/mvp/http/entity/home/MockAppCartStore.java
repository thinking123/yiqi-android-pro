package com.eshop.mvp.http.entity.home;

import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.cart.CartStore;

import java.util.ArrayList;
import java.util.List;

public class MockAppCartStore {

    public static List<AppGoodsSection> goodsSectionList;
    public static List<AppGoods> goodsList1;
    public static List<AppGoods> goodsList2;
    public static List<AppGoods> goodsList3;
    public static List<AppGoods> goodsList4;

    public static List<CartStore> monthStore;
    public static List<CartStore> otherStore;

    public static AppCartStore appCartStore;

    public static void init(){

        goodsSectionList = new ArrayList<>();
        goodsList1 = new ArrayList<>();
        goodsList2 = new ArrayList<>();
        goodsList3 = new ArrayList<>();
        goodsList4 = new ArrayList<>();


        AppGoods b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.title = "飞科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "腾讯科技有限公司";
        b1.unitPrice = 169;
        b1.storeId =1;
        b1.carId = 11;
        b1.isChecked = false;

        goodsList1.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "腾讯科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =1;
        b1.carId = 12;
        b1.isChecked = false;

        goodsList1.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "腾讯科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =1;
        b1.carId = 13;
        b1.isChecked = false;

        goodsList1.add(b1);


        /////
        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.title = "飞科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 169;
        b1.storeId =2;
        b1.carId = 14;
        b1.isChecked = false;

        goodsList2.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =2;
        b1.carId = 15;
        b1.isChecked = false;

        goodsList2.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =2;
        b1.carId = 16;
        b1.isChecked = false;

        goodsList2.add(b1);

        ///
        /////
        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.title = "飞科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 169;
        b1.storeId =3;
        b1.carId = 17;
        b1.isChecked = false;

        goodsList3.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =3;
        b1.carId = 18;
        b1.isChecked = false;

        goodsList3.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =3;
        b1.carId = 19;
        b1.isChecked = false;

        goodsList3.add(b1);

        /////
        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.title = "飞科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 169;
        b1.storeId =4;
        b1.carId = 20;
        b1.isChecked = false;

        goodsList4.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =4;
        b1.carId = 21;
        b1.isChecked = false;

        goodsList4.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "华为科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =4;
        b1.carId = 22;
        b1.isChecked = false;

        goodsList4.add(b1);

        ////

        CartStore cartStore1 = new CartStore();
        cartStore1.id = 1;
        cartStore1.streoName = "腾讯科技有限公司";
        cartStore1.appgoodslsit = goodsList1;
        cartStore1.isChecked = false;

        CartStore cartStore2 = new CartStore();
        cartStore2.id = 2;
        cartStore2.streoName = "华为科技有限公司";
        cartStore2.appgoodslsit = goodsList2;
        cartStore2.isChecked = false;

        monthStore = new ArrayList<>();
        monthStore.add(cartStore1);
        monthStore.add(cartStore2);

        CartStore cartStore3 = new CartStore();
        cartStore3.id = 3;
        cartStore3.streoName = "腾讯科技有限公司";
        cartStore3.appgoodslsit = goodsList3;
        cartStore3.isChecked = false;

        CartStore cartStore4 = new CartStore();
        cartStore4.id = 4;
        cartStore4.streoName = "华为科技有限公司";
        cartStore4.appgoodslsit = goodsList4;
        cartStore4.isChecked = false;


        otherStore = new ArrayList<>();
        otherStore.add(cartStore3);
        otherStore.add(cartStore4);

        appCartStore = new AppCartStore();

        appCartStore.monthStore = monthStore;
        appCartStore.otherStore = otherStore;

    }

    /**
     * 以商铺分组，月结和非月结是两大类
     * @param store
     * @return
     */
    public static List<AppGoodsSection> getAppGoodsSection(int type,AppCartStore store){
        List<AppGoodsSection> appGoodsSectionList = new ArrayList();

        if(store==null)return appGoodsSectionList;

        if(store.otherStore!=null && type==0){
            for(CartStore cartStore:store.otherStore){
                AppGoods appGoods_head = new AppGoods();
                appGoods_head.streoName = cartStore.streoName;
                appGoods_head.storeId = cartStore.id;
                appGoods_head.isChecked = false;
                appGoods_head.isMonth = false; //本类非月结商品

                AppGoodsSection appGoodsSection = new AppGoodsSection(appGoods_head);
                appGoodsSection.isHeader = true;
                appGoodsSectionList.add(appGoodsSection);

                //int k=0;
                //int length = cartStore.appgoodslsit.size();
                for(AppGoods appGoods : cartStore.appgoodslsit){
                    appGoods.isChecked = false;
                    appGoods.isMonth = false;
                    appGoods.isHead = false;
                    appGoods.isFoot = false;
                    appGoods.streoName = cartStore.streoName;
                    appGoods.storeId = cartStore.id;

                   // if(k==0)appGoods.isHead = true;
                   // else appGoods.isHead = false;

                   // if(k==length-1)appGoods.isFoot = true;
                   // else appGoods.isFoot = false;

                   // k++;

                    appGoodsSection = new AppGoodsSection(appGoods);
                    appGoodsSection.isHeader = false;
                    appGoodsSectionList.add(appGoodsSection);
                }
            }
        }

        if(store.monthStore!=null  && type==1){
            for(CartStore cartStore:store.monthStore){
                AppGoods appGoods_head = new AppGoods();
                appGoods_head.streoName = cartStore.streoName;
                appGoods_head.storeId = cartStore.id;
                appGoods_head.isChecked = false;
                appGoods_head.isMonth = true; //本类是月结商品

                AppGoodsSection appGoodsSection = new AppGoodsSection(appGoods_head);
                appGoodsSection.isHeader = true;
                appGoodsSectionList.add(appGoodsSection);

                //int k=0;
                //int length = cartStore.appgoodslsit.size();
                for(AppGoods appGoods : cartStore.appgoodslsit){
                    appGoods.isChecked = false;
                    appGoods.isMonth = true;
                    appGoods.isHead = false;
                    appGoods.isFoot = false;
                    appGoods.storeId = cartStore.id;
                    appGoods.streoName = cartStore.streoName;

                   // if(k==0)appGoods.isHead = true;
                   // else appGoods.isHead = false;

                   // if(k==length-1)appGoods.isFoot = true;
                   // else appGoods.isFoot = false;

                   // k++;

                    appGoodsSection = new AppGoodsSection(appGoods);
                    appGoodsSection.isHeader = false;
                    appGoodsSectionList.add(appGoodsSection);
                }
            }
        }

        return appGoodsSectionList;
    }

}