package com.eshop.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.Category;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 左边一级分类
 */
public class IndexLevel1ViewHolder extends ExRvItemViewHolderBase {

    @BindView(R.id.rlRoot)
    RelativeLayout mRlRoot;

    @BindView(R.id.tvName)
    TextView mTvName;

    @BindView(R.id.ivTip)
    ImageView mIvTip;

    public IndexLevel1ViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.page_discover_index_level1_vh);
        ButterKnife.bind(this, itemView);
    }


    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Category oper, boolean isSelected) {

        mTvName.setText(oper == null ? TextUtil.TEXT_EMPTY : oper.getName());
        if (isSelected)
            setSelectedStyle();
        else
            setNormalStyle();
    }

    public void setSelectedStyle() {

        mIvTip.setVisibility(View.VISIBLE);
        mTvName.setTextColor(0XFFFF2A24);
        mTvName.setTextSize(13.4f);
        mRlRoot.setBackgroundColor(0XFFFFFF);
    }

    public void setNormalStyle() {

        mIvTip.setVisibility(View.INVISIBLE);
        mTvName.setTextColor(0XFF444444);
        mTvName.setTextSize(12.5f);
        mRlRoot.setBackgroundColor(0XFFF6F6F6);
    }
}
