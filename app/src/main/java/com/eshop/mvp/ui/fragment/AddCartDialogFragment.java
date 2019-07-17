package com.eshop.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.R;
import com.eshop.mvp.ui.widget.AmountView;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link AddCartDialogFragment.Listener}.</p>
 */
public class AddCartDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_PRICE = "item_price";
    private static final String ARG_ITEM_IMG = "item_img";

    private Listener mListener;

    private ImageView goods_img;
    private TextView name_txt;
    private TextView price_txt;
    private AmountView amountView;
    private Button btnAdd;

    private String img;
    private String name;
    private double price;

    private int amount = 1;

    // TODO: Customize parameters
    public static AddCartDialogFragment newInstance(String name, double price, String img) {
        final AddCartDialogFragment fragment = new AddCartDialogFragment();
        fragment.img = img;
        fragment.name = name;
        fragment.price = price;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_add_cart_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        goods_img = view.findViewById(R.id.goods_img);
        name_txt = view.findViewById(R.id.name);
        price_txt = view.findViewById(R.id.price);
        amountView = view.findViewById(R.id.amount_view);
        btnAdd = view.findViewById(R.id.add);

        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                AddCartDialogFragment.this.amount = amount;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){

                    mListener.onItemClicked(amount);
                    dismiss();
                }
            }
        });

        if (img != null) {

            Glide.with(getActivity())
                    .load(img)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(goods_img);
        }

        if (name != null) {
            name_txt.setText(name);
        }

        if (price != 0) {
            price_txt.setText(price + "");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onItemClicked(int count);
    }


}
