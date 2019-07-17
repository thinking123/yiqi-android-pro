package com.eshop.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.mvp.http.entity.category.Category;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.fresco.FrescoImageView;
import com.zkteam.discover.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 右边分类item
 */
public class IndexLevel2MiniViewHolder extends ExRvItemViewHolderBase {

    @BindView(R.id.fivCover)
    FrescoImageView mFivCover;

    @BindView(R.id.tvName)
    TextView mTvName;

    public IndexLevel2MiniViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.page_discover_index_level2_vh_mini);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Category oper) {

        if (oper == null) {

            mTvName.setText(TextUtil.TEXT_EMPTY);
            mFivCover.setImageUriByLp((String) null);
        } else {

            mTvName.setText(oper.getName());
            mFivCover.setImageUriByLp(oper.getCategoryIcon());
        }
    }
}
