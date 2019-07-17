package com.eshop.mvp.http.entity.home;

import android.content.Context;

import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.utils.PinyinComparator;
import com.eshop.mvp.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockBrandBean {

    public static List<BrandBeanSection> brandSectionList;
    public static List<BrandBean> mDateList;

    public static void init(Context context){

        brandSectionList = new ArrayList<>();

        mDateList = filledData(context.getResources().getStringArray(R.array.date));

        PinyinComparator mComparator = new PinyinComparator();

        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);

        int k=0;
        String zm="A";
        for(BrandBean brandBean : mDateList){
            if(k==0 || !brandBean.brandZm.equalsIgnoreCase(zm)){
                BrandBean b = new BrandBean();
                b.brandZm = brandBean.brandZm;
                b.brandImg = brandBean.brandImg;
                b.brandName = brandBean.brandName;
                b.id = brandBean.id;
                BrandBeanSection brandBeanSection = new BrandBeanSection(b);
                brandBeanSection.isHeader = true;
                brandBeanSection.header = brandBean.brandZm;
                brandSectionList.add(brandBeanSection);
                zm = brandBean.brandZm;

                BrandBeanSection brandBeanSection1 = new BrandBeanSection(brandBean);
                brandBeanSection1.isHeader = false;
                brandSectionList.add(brandBeanSection1);

            }else{
                BrandBeanSection brandBeanSection = new BrandBeanSection(brandBean);
                brandBeanSection.isHeader = false;
                brandSectionList.add(brandBeanSection);
            }
            k++;
        }

    }

    public static List<BrandBean> filledData(String[] date) {
        List<BrandBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            BrandBean brandBean = new BrandBean();
            brandBean.brandName=date[i];
            brandBean.brandImg="http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                brandBean.brandZm=sortString.toUpperCase();
            } else {
                brandBean.brandZm = "#";
            }

            mSortList.add(brandBean);
        }
        return mSortList;

    }

    public static List<BrandBean> getBrandList(BrandBeanList brandBeanList){
        List<BrandBean> list = new ArrayList<>();
        for(AppBrandList appBrandList : brandBeanList.appBrandlist){
            for(BrandBean brandBean : appBrandList.appBrandlist){
                list.add(brandBean);
            }
        }
        return list;
    }

    public static void sortData(List<BrandBean> data) {
        brandSectionList.clear();
        mDateList = data;
        PinyinComparator mComparator = new PinyinComparator();

        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);

        int k=0;
        String zm="A";
        for(BrandBean brandBean : mDateList){
            if(k==0 || !brandBean.brandZm.equalsIgnoreCase(zm)){
                BrandBean b = new BrandBean();
                b.brandZm = brandBean.brandZm;
                b.brandImg = brandBean.brandImg;
                b.brandName = brandBean.brandName;
                b.id = brandBean.id;
                BrandBeanSection brandBeanSection = new BrandBeanSection(b);
                brandBeanSection.isHeader = true;
                brandBeanSection.header = brandBean.brandZm;
                brandSectionList.add(brandBeanSection);
                zm = brandBean.brandZm;

                BrandBeanSection brandBeanSection1 = new BrandBeanSection(brandBean);
                brandBeanSection1.isHeader = false;
                brandSectionList.add(brandBeanSection1);

            }else{
                BrandBeanSection brandBeanSection = new BrandBeanSection(brandBean);
                brandBeanSection.isHeader = false;
                brandSectionList.add(brandBeanSection);
            }
            k++;
        }
    }

}