package com.eshop.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.Category;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.util.TextUtil;
import com.zkteam.discover.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 右边大分类
 */
public class IndexLevel2TitleViewHolder extends ExRvItemViewHolderBase {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    @BindView(R.id.tvMore)
    TextView mTvMore;

    public IndexLevel2TitleViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.page_discover_index_level2_vh_title);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Category oper) {

        if (oper == null) {

            mTvTitle.setText(TextUtil.TEXT_EMPTY);
            ViewUtil.hideView(mTvMore);
        } else {

            mTvTitle.setText(oper.getName());
        }

    }
}
