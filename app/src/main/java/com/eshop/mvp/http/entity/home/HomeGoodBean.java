package com.eshop.mvp.http.entity.home;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * appClassId (integer, optional): 类型 33--月结 ,
 * id (integer, optional): 主键 ,
 * imgUrl (string, optional): 商品图片连接 ,
 * state (string, optional): 商品状态 0 正常 1下架 ,
 * title (string, optional): 标题 ,
 * unitPrice (string, optional): 商品价格
 **/

public class HomeGoodBean implements Serializable {
     public int id;
     public String imgUrl;
     public String title;
     public String unitPrice;
     public String state;
     public int appClassId;
}
