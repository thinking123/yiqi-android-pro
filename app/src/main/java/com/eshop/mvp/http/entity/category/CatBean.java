package com.eshop.mvp.http.entity.category;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.http.entity.category
 **/


public class CatBean  {
    public Integer id;
    public Integer parentId;
    public String categoryName;
    public Integer categoryOrder;
    public Integer categoryState;
    public String  createTime;
    public String categoryIcon;
    public String parentName;

    public boolean isselected = false;


}
