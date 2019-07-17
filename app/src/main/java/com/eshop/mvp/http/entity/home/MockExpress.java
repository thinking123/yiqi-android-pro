package com.eshop.mvp.http.entity.home;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.ExpressTime;

import java.util.ArrayList;
import java.util.List;

public class MockExpress {
    public static List<ExpressTime> expressTimeList;

    public static ExpressState expressState;

    public static void init(){
        expressTimeList = new ArrayList<>();
        expressState = new ExpressState();

        ExpressTime e1 = new ExpressTime();
        e1.position = 0;
        e1.setContext("快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中");
        e1.setFtime("2018-12-11");
        expressTimeList.add(e1);

        e1 = new ExpressTime();
        e1.position = 1;
        e1.setContext("快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中");
        e1.setFtime("2018-12-11");
        expressTimeList.add(e1);

        e1 = new ExpressTime();
        e1.position = 2;
        e1.setContext("快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中");
        e1.setFtime("2018-12-11");
        expressTimeList.add(e1);

        e1 = new ExpressTime();
        e1.position = 3;
        e1.setContext("快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中");
        e1.setFtime("2018-12-11");
        expressTimeList.add(e1);

        e1 = new ExpressTime();
        e1.position = 4;
        e1.setContext("快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中");
        e1.setFtime("2018-12-11");
        expressTimeList.add(e1);

        e1 = new ExpressTime();
        e1.position = 5;
        e1.setContext("快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中快递正在托运中");
        e1.setFtime("2018-12-11");
        expressTimeList.add(e1);

        expressState.expressCompany = "韵达快递";
        expressState.expressCompanyName = "韵达";
        expressState.expressNumber = "1234567";
        expressState.state = "0";

    }

    /**
     * 0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单
     * @param state
     * @return
     */
    public static String getState(String state){
        String msg = "";
        switch (state){
            case "0":
                msg = "在途中";
                break;
            case "1":
                msg = "已揽收";
                break;
            case "2":
                msg = "疑难";
                break;
            case "3":
                msg = "已签收";
                break;
            case "4":
                msg = "退签";
                break;
            case "5":
                msg = "同城派送中";
                break;
            case "6":
                msg = "退回";
                break;
            case "7":
                msg = "转单";
                break;
        }

        return msg;
    }

}