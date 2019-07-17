package com.eshop.mvp.http.entity.home;

import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class MockAfterSaleOrder {
    public static AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
    public static AfterSaleOrder.PageUtilBean pageUtil = new AfterSaleOrder.PageUtilBean();
    public static AfterSaleOrder.AfterSaleTabsBean afterSaleTabsBean = new AfterSaleOrder.AfterSaleTabsBean();

    public static void init(int status) {
        // if(status==0) {
        afterSaleOrder.setPageUtil(pageUtil);
        afterSaleOrder.setAfterSaleTabs(new ArrayList<>());


    }

    public static List<AfterSaleOrder.AfterSaleTabsBean.GoodsListBean> getData(AfterSaleOrder appOrder) {
        List<AfterSaleOrder.AfterSaleTabsBean.GoodsListBean> appGoodsList = new ArrayList<>();

        AfterSaleOrder.AfterSaleTabsBean.GoodsListBean lastAppGoods = null;

        for (AfterSaleOrder.AfterSaleTabsBean a : appOrder.getAfterSaleTabs()) {

            AfterSaleOrder.AfterSaleTabsBean headinfo = new AfterSaleOrder.AfterSaleTabsBean();
            headinfo.setId(a.getId());
            headinfo.setGoodsList(null);
            headinfo.setOrederId(a.getOrederId());
            headinfo.setPromptMessage(a.getPromptMessage());
            headinfo.setState(a.getState());
            headinfo.setStoreId(a.getStoreId());
            headinfo.setStreoName(a.getStreoName());
            headinfo.setTotalNum(a.getTotalNum());
            headinfo.setTotalPrice(a.getTotalPrice());

            int k = 0;

            for (AfterSaleOrder.AfterSaleTabsBean.GoodsListBean appGoods : a.getGoodsList()) {


                if (k == 0){
                    appGoods.isHead = true;
                }
                else {
                    appGoods.isHead = false;
                }
                k++;
                appGoods.afterSaleTabsBean = headinfo;

                appGoodsList.add(appGoods);
                lastAppGoods = appGoods;
            }

            lastAppGoods.isFoot = true;

        }

        return appGoodsList;
    }
}
