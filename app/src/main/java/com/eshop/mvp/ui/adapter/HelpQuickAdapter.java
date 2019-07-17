package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.store.HelpBean;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class HelpQuickAdapter extends BaseQuickAdapter<HelpBean, BaseViewHolder> {

    public HelpQuickAdapter(@Nullable List<HelpBean> data) {
        super(R.layout.adapter_item_help, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpBean item) {


        helper.setText(R.id.tv_name,item.question);

        helper.getView(R.id.content).setOnClickListener(v -> {
            if(onClickHelpItemListener!=null){
                onClickHelpItemListener.onClickItem(item);
            }
        });

    }

    public interface OnClickHelpItemListener {
        void onClickItem(HelpBean helpBean);
    }

    public HelpQuickAdapter.OnClickHelpItemListener onClickHelpItemListener;

    public void setOnClickHelpItemListener(HelpQuickAdapter.OnClickHelpItemListener l) {
        this.onClickHelpItemListener = l;
    }
}
