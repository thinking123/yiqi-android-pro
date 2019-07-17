package com.eshop.mvp.http.entity.product;


import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/24
 * @Package com.eshop.mvp.http.entity.product
 **/

/**
 * {
 *     "collectionGoodsState": "string",
 *     "collectionNum": "string",
 *     "detailMapList": [
 *       "string"
 *     ],
 *     "details": "string",
 *     "freight": "string",
 *     "freightState": 0,
 *     "freightStateMsg": "string",
 *     "id": 0,
 *     "monthlySalesNum": "string",
 *     "rotationChartList": [
 *       "string"
 *     ],
 *     "storeArea": "string",
 *     "storeId": 0,
 *     "storeImg": "string",
 *     "streoName": "string",
 *     "title": "string",
 *     "unitPrice": "string"
 */
public class ProductDetail implements Serializable{

    public String collectionGoodsState;
    public String collectionNum;
    public List<String> detailMapList;
    public String details;
    public int appClassId;
    public String freight;
    public int freightState;
    public String freightStateMsg;
    public int id;
    public String monthlySalesNum;
    public List<String> rotationChartList;
    public String storeArea;
    public int storeId;
    public String storeImg;
    public String streoName;
    public String title;
    public double unitPrice;
    //环信Id
    public String huanxinId;

}
