package com.eshop.mvp.http.entity.product;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MockProductDetail {

    public static String collectionGoodsState = "0";
    public static String collectionNum = "10";
    public static List<String> detailMapList= new ArrayList<>();
    public static String details = "详情描述";
    public static String freight = "12.5";
    public static int freightState = 0;
    public static String freightStateMsg = "免运费";
    public static int id = 9;
    public static String monthlySalesNum = "100";
    public static List<String> rotationChartList = new ArrayList<>();
    public static String storeArea = "深圳市福田区";
    public static int storeId = 10;
    public static String storeImg = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548339971582&di=0086b6b8b9fe38f9c801c38ef557ed3e&imgtype=0&src=http%3A%2F%2Fdn-kdt-img.qbox.me%2Fupload_files%2F2014%2F11%2F01%2FFrMqTUZuMBn8gu3vU3-ddkYkmRpG.jpg";
    public static String streoName = "深圳市腾讯科技有限公司";
    public static String title = "进口一分两路球阀";
    public static double unitPrice = 237.8;

    public static ProductDetail productDetail;

    public static void init(){
        MockProductDetail.rotationChartList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548339971582&di=0086b6b8b9fe38f9c801c38ef557ed3e&imgtype=0&src=http%3A%2F%2Fdn-kdt-img.qbox.me%2Fupload_files%2F2014%2F11%2F01%2FFrMqTUZuMBn8gu3vU3-ddkYkmRpG.jpg");
        productDetail = new ProductDetail();
        productDetail.collectionGoodsState = MockProductDetail.collectionGoodsState;
        productDetail.collectionNum = MockProductDetail.collectionNum;
        productDetail.details = MockProductDetail.details;
        productDetail.freight = MockProductDetail.freight;
        productDetail.freightState = MockProductDetail.freightState;
        productDetail.id = MockProductDetail.id;
        productDetail.freightStateMsg = MockProductDetail.freightStateMsg;
        productDetail.monthlySalesNum = MockProductDetail.monthlySalesNum;
        productDetail.storeArea = MockProductDetail.storeArea;
        productDetail.storeId = MockProductDetail.storeId;
        productDetail.storeImg = MockProductDetail.storeImg;
        productDetail.unitPrice = MockProductDetail.unitPrice;
        productDetail.streoName = MockProductDetail.streoName;
        productDetail.title = MockProductDetail.title;
    }

}
