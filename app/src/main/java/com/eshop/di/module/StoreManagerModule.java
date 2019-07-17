package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.store.AuditGoods;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.Transaction;
import com.eshop.mvp.http.entity.store.WithDraw;
import com.eshop.mvp.model.HomeModel;
import com.eshop.mvp.model.LoginModel;
import com.eshop.mvp.model.ProductDetailsModel;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.ui.adapter.BankSetQuickAdapter;
import com.eshop.mvp.ui.adapter.CatSetQuickAdapter;
import com.eshop.mvp.ui.adapter.InnerCatQuickAdapter;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.RecordQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreCatQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreGoodsQuickAdapter;
import com.eshop.mvp.ui.adapter.TransactionQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.model.StoreManagerModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class StoreManagerModule {
//public abstract class StoreManagerModule {

   // @Binds
   // abstract StoreManagerContract.Model bindStoreManagerModel(StoreManagerModel model);

    StoreManagerContract.View view;

    public StoreManagerModule(StoreManagerContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public StoreManagerContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new StoreManagerModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public StoreManagerContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public ProductDetailsModel provideProductDetailsModel(IRepositoryManager repositoryManager) {
        return new ProductDetailsModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public HomeModel provideHomeModel(IRepositoryManager repositoryManager) {
        return new HomeModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public LoginModel provideLoginModel(IRepositoryManager repositoryManager) {
        return new LoginModel(repositoryManager);
    }


    @ActivityScope
    @Provides
    public List<StoreCat> provideStoreColumns() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public CatSetQuickAdapter prodvideCatSetQuickAdapter(
            List<StoreCat> storeColumns, Application application) {
        CatSetQuickAdapter catSetQuickAdapter = new CatSetQuickAdapter(storeColumns);
        return catSetQuickAdapter;

    }

    @ActivityScope
    @Provides
    public InnerCatQuickAdapter prodvideInnerCatQuickAdapter(
            List<StoreCat> storeCats, Application application) {
        InnerCatQuickAdapter innerCatQuickAdapter = new InnerCatQuickAdapter(storeCats);
        return innerCatQuickAdapter;

    }

    @ActivityScope
    @Provides
    public List<AuditGoods> provideAuditGoodsList() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public StoreGoodsQuickAdapter prodvideStoreGoodsQuickAdapter(
            List<AuditGoods> auditGoodsList, Application application) {
        StoreGoodsQuickAdapter storeGoodsQuickAdapter = new StoreGoodsQuickAdapter(auditGoodsList);
        return storeGoodsQuickAdapter;

    }


    @ActivityScope
    @Provides
    public List<Transaction> provideTransactionList() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public TransactionQuickAdapter prodvideTransactionQuickAdapter(
            List<Transaction> transactionList, Application application) {
        TransactionQuickAdapter transactionQuickAdapter = new TransactionQuickAdapter(transactionList);
        return transactionQuickAdapter;

    }

    @ActivityScope
    @Provides
    public List<WithDraw> provideWithDrawList() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public RecordQuickAdapter prodvideRecordQuickAdapter(
            List<WithDraw> withDrawList, Application application) {
        RecordQuickAdapter recordQuickAdapter = new RecordQuickAdapter(withDrawList);
        return recordQuickAdapter;

    }


    @ActivityScope
    @Provides
    public List<BankCard> provideBankCardList() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public BankSetQuickAdapter prodvideBankSetQuickAdapter(
            List<BankCard> bankCardList, Application application) {
        BankSetQuickAdapter bankSetQuickAdapter = new BankSetQuickAdapter(bankCardList);
        return bankSetQuickAdapter;

    }
}