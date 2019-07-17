package com.eshop.mvp.http.entity.home;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.ship.AddressBean;

import java.util.ArrayList;
import java.util.List;

public class MockAddress {
    public static List<AddressBean> addressList;

    public static void init(){
        addressList = new ArrayList<>();

        AddressBean c1 = new AddressBean();
        c1.isDefault = 1;
        c1.receiveUserName = "张三";
        c1.receivePhone = "13928439999";
        c1.address = "重庆市九龙坡区";
        c1.id = 11;
        c1.userId = 9;
        addressList.add(c1);

        c1 = new AddressBean();
        c1.isDefault = 0;
        c1.receiveUserName = "李四";
        c1.receivePhone = "13928439999";
        c1.address = "重庆市云阳";
        c1.id = 12;
        c1.isSelected = true;
        c1.userId = 9;
        addressList.add(c1);

    }


}