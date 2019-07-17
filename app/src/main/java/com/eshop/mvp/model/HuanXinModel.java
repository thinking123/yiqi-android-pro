package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.HuanXinService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.huanxin.ChatRoom;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import com.eshop.mvp.contract.HuanXinContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 12:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HuanXinModel extends BaseModel implements HuanXinContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HuanXinModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<String>> addChatRoom(String description, String maxusers, String name, String owner) {
        return mRepositoryManager.obtainRetrofitService(HuanXinService.class)
                .addChatRoom(new ChatRoom(description, maxusers, name, owner));
    }

}