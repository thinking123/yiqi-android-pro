package com.eshop.mvp.utils;

import android.content.Context;
import android.content.Intent;

import com.eshop.app.base.BaseApp;
import com.eshop.app.base.LoginConfig;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.ui.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginUtils {

    public static String KEYWORD="keyword";

    public static boolean isLogin(Context context){
        if(BaseApp.loginBean!=null && BaseApp.loginBean.getToken()!=null && !BaseApp.loginBean.getToken().isEmpty()){
            return true;
        }else{
            if(setLogin(context) && BaseApp.loginBean.getToken()!=null && !BaseApp.loginBean.getToken().isEmpty()){
                return true;
            }else{
               // Intent intent = new Intent(context,LoginActivity.class);
               // context.startActivity(intent);
                return false;
            }
        }

    }

    public static boolean setLogin(Context context){
        if(BaseApp.loginBean==null){
            BaseApp.loginBean = new LoginBean();
        }
        if(SpUtils.contains(context,LoginConfig.TOKEN)) {

            String token = (String) SpUtils.get(context, LoginConfig.TOKEN, "");
            if(token.isEmpty())return false;

           // BaseApp.loginBean.setToken((String) SpUtils.get(context, LoginConfig.TOKEN, ""));
           // BaseApp.loginBean.setId((Integer) SpUtils.get(context, LoginConfig.ID, 0));

            BaseApp.loginBean.setId((Integer) SpUtils.get(context, LoginConfig.ID, 0));
            BaseApp.loginBean.setToken((String) SpUtils.get(context, LoginConfig.TOKEN, ""));
            BaseApp.loginBean.setPhone((String) SpUtils.get(context, LoginConfig.PHONE, ""));
            BaseApp.loginBean.setLogo((String) SpUtils.get(context, LoginConfig.LOGO, ""));
            BaseApp.loginBean.setNickNmae((String) SpUtils.get(context, LoginConfig.NICKNAME, ""));
            BaseApp.loginBean.setStoreId((Integer) SpUtils.get(context, LoginConfig.STOREID, 0));
            BaseApp.loginBean.setState((Integer) SpUtils.get(context, LoginConfig.STATE, 0));

            BaseApp.loginBean.setOpenId((String) SpUtils.get(context, LoginConfig.OPEN_ID, ""));
            BaseApp.loginBean.setDeviceId((String) SpUtils.get(context, LoginConfig.DEVICE_ID, ""));
            BaseApp.loginBean.setSex((Integer) SpUtils.get(context, LoginConfig.SEX, 0));
            BaseApp.loginBean.setApplyMsg((String) SpUtils.get(context, LoginConfig.APPLYMSG, ""));
            BaseApp.loginBean.setIsMonth((Integer) SpUtils.get(context, LoginConfig.ISMONTH, 0));
            BaseApp.loginBean.setMonthMsg((String) SpUtils.get(context, LoginConfig.MONTHMSG, ""));
            BaseApp.loginBean.setIsRegister((String) SpUtils.get(context, LoginConfig.ISREGISTER, ""));
            BaseApp.loginBean.setCollectStoreNum((Integer) SpUtils.get(context, LoginConfig.COLLECTSTORENUM, 0));
            BaseApp.loginBean.setCollectProductNum((Integer) SpUtils.get(context, LoginConfig.COLLECTPRODUCTNUM, 0));

            BaseApp.loginBean.setHuanxinId((String)SpUtils.get(context , LoginConfig.HUANXINID , ""));

            return true;
        }else{
            BaseApp.loginBean = null;

            return false;
        }
    }

    public static void saveLogin(Context context) {
        if (BaseApp.loginBean == null) {
            return;
        }

        //SpUtils.put(context,LoginConfig.TOKEN,BaseApp.loginBean.getToken());
        //SpUtils.put(context,LoginConfig.ID,BaseApp.loginBean.getId());

        SpUtils.put(context,LoginConfig.ID,BaseApp.loginBean.getId());
        SpUtils.put(context,LoginConfig.TOKEN,BaseApp.loginBean.getToken());
        SpUtils.put(context,LoginConfig.PHONE,BaseApp.loginBean.getPhone());
        SpUtils.put(context,LoginConfig.LOGO,BaseApp.loginBean.getLogo());
        SpUtils.put(context,LoginConfig.NICKNAME,BaseApp.loginBean.getNickNmae());
        SpUtils.put(context,LoginConfig.STOREID,BaseApp.loginBean.getStoreId());
        SpUtils.put(context,LoginConfig.STATE,BaseApp.loginBean.getState());

        SpUtils.put(context,LoginConfig.OPEN_ID,BaseApp.loginBean.getOpenId());
        SpUtils.put(context,LoginConfig.DEVICE_ID,BaseApp.loginBean.getDeviceId());
        SpUtils.put(context,LoginConfig.SEX,BaseApp.loginBean.getSex());
        SpUtils.put(context,LoginConfig.APPLYMSG,BaseApp.loginBean.getApplyMsg());
        SpUtils.put(context,LoginConfig.ISMONTH,BaseApp.loginBean.getIsMonth());
        SpUtils.put(context,LoginConfig.MONTHMSG,BaseApp.loginBean.getMonthMsg());
        SpUtils.put(context,LoginConfig.ISREGISTER,BaseApp.loginBean.getIsRegister());
        SpUtils.put(context,LoginConfig.COLLECTSTORENUM,BaseApp.loginBean.getCollectStoreNum());
        SpUtils.put(context,LoginConfig.COLLECTPRODUCTNUM,BaseApp.loginBean.getCollectProductNum());
        SpUtils.put(context,LoginConfig.HUANXINID,BaseApp.loginBean.getHuanxinId());
        SpUtils.put(context,LoginConfig.CHATROOMID,BaseApp.loginBean.getChatRoomId());

    }

    public static void login(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    public static void saveKeyWord(Context context,String word){

        String words = (String)SpUtils.get(context,KEYWORD,"");

        if(words.isEmpty())words=word;
        else{
            words = words + ","+word;
        }

        SpUtils.put(context,KEYWORD,words);
    }

    public static List<String> getkeyWord(Context context){
        List<String> list = new ArrayList<>();
        String words = (String)SpUtils.get(context,KEYWORD,"");

        if(!words.isEmpty()) {

            String[] datas = words.split(",");

            list = Arrays.asList(datas);
        }

        return list;
    }

    public static void clearKeyWord(Context context){
        SpUtils.remove(context,KEYWORD);
    }
}
