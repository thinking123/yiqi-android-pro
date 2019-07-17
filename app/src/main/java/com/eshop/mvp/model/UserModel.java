package com.eshop.mvp.model;

import com.eshop.mvp.http.api.service.ProductService;
import com.eshop.mvp.http.api.service.UploadService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.product.CollectNum;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.eshop.mvp.contract.SelfContract;
import com.eshop.mvp.http.api.service.UserService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * @Author shijun
 * @Data 2019/1/12
 * @Package com.eshop.mvp.model
 **/
public class UserModel extends BaseModel implements SelfContract.Model {
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<MyBaseResponse<String>> upLoadImage(MultipartBody.Part upload_file) {
        return mRepositoryManager.obtainRetrofitService(UploadService.class)
                .uploadImage(upload_file);
    }

    public Observable<BaseResponse<UserBean>> getUserInfo() {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .userDetails(null);
    }

    public Observable<BaseResponse<UserBean>> getUserInfo(int userId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .userDetails(userId);
    }

    public Observable<BaseResponse> updateInfo(String username, Integer sex) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updateInfo(username,sex);
    }

    public Observable<BaseResponse> updatePassword(String password) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updatePassword(password);
    }

    public Observable<BaseResponse<String>> updateUserImage(MultipartBody.Part upload_file) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updateUserImage(upload_file);
    }

    @Override
    public Observable<MyBaseResponse<CollectNum>> getCollectionNum(String token) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .getCollectionNum(token);
    }
}
