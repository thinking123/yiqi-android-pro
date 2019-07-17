package com.eshop.mvp.http.entity.home;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.product.StoreCat;

import java.util.ArrayList;
import java.util.List;

public class MockStoreCats {
    public static List<StoreCat> catsList;

    public static void init(){
        catsList = new ArrayList<>();

        StoreCat c1 = new StoreCat();
        c1.id = 1;
        c1.categoryName = "空气开关";
        catsList.add(c1);

        c1 = new StoreCat();
        c1.id = 2;
        c1.categoryName = "低压产品";
        catsList.add(c1);

        c1 = new StoreCat();
        c1.id = 3;
        c1.categoryName = "变频器";
        catsList.add(c1);

    }


}