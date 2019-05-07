package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.huanxin.ChatRoom;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HuanXinService {
    @POST("/chattoom/addRoom")
    Observable<BaseResponse<String>> addChatRoom(@Body ChatRoom chatRoom);
}
