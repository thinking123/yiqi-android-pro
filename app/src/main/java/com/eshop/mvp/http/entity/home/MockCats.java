package com.eshop.mvp.http.entity.home;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;

import java.util.ArrayList;
import java.util.List;

public class MockCats {
    public static List<CatBean> catsList;

    public static void init(){
        catsList = new ArrayList<>();

        CatBean c1 = new CatBean();
        c1.id = R.drawable.c1;
        c1.categoryName = "电磁阀";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c2;
        c1.categoryName = "气源过滤器";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c3;
        c1.categoryName = "气缸";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c4;
        c1.categoryName = "气管";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c5;
        c1.categoryName = "数显压力表";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c6;
        c1.categoryName = "磁性开关";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c7;
        c1.categoryName = "直线导轨滑件";
        catsList.add(c1);

        c1 = new CatBean();
        c1.id = R.drawable.c8;
        c1.categoryName = "全部";
        catsList.add(c1);
    }


}