package com.eshop.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     CityListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link CityListDialogFragment.Listener}.</p>
 */
public class CityListDialogFragment extends BottomSheetDialogFragment implements CityFragment.CitySelectListener {


    private static final String ARG_ITEM_COUNT = "item_count";
    private Listener mListener;

    private CityListDialogFragment.Adapter adapter;

    private TabLayout tabLayout;
    private ViewPager viewpager;
    private TextView cancel;
    private TextView ok;

    private String province = "";
    private String city = "";

    private final List<CityFragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
            = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 960);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        final View view = getView();
        view.post(new Runnable() {
            @Override
            public void run() {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                mBottomSheetBehavior = (BottomSheetBehavior) behavior;
                mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                //设置高度
                int height = 960;// display.getHeight() / 2;
                mBottomSheetBehavior.setPeekHeight(height);

                //parent.setBackgroundColor(Color.TRANSPARENT);
            }
        });
    }

    public static CityListDialogFragment newInstance(Listener l) {
        final CityListDialogFragment fragment = new CityListDialogFragment();
        fragment.mListener = l;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewpager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager);
        //设置可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        province = "";
        city = "";

        if (viewpager != null) {
            setupViewPager(getActivity(), viewpager);
            setupViewPager(0,viewpager, "", "请选择");
        }

        cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = province+city;
                mListener.onCitySelected(address);
                dismiss();
            }
        });
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

    @Override
    public void onSelected(int page,String caption, String cityCode) {

        if(page==2){
            String address = mFragmentTitles.get(0)+mFragmentTitles.get(1)+caption;
            mListener.onCitySelected(address);
            dismiss();
        }else {
            if(page==0){
                BaseApp.province = cityCode;
                province = caption;
            }
            if(page==1){
                BaseApp.city = cityCode;
                city = caption;
            }
            setupViewPager(page + 1, viewpager, cityCode, caption);

            adapter.notifyDataSetChanged();
            viewpager.setCurrentItem(adapter.getCount() - 1);
        }
    }

    public interface Listener {
        void onCitySelected(String city);
    }

    private void setupViewPager(FragmentActivity context, ViewPager viewPager) {
        adapter = new CityListDialogFragment.Adapter(getChildFragmentManager());

        //不加这句滑动报错ViewHolder views must not be attached when created
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);

        viewPager.setAdapter(adapter);

        CityFragment cityFragment1 = CityFragment.newInstance(0,"",CityListDialogFragment.this);
        CityFragment cityFragment2 = CityFragment.newInstance(1,"",CityListDialogFragment.this);
        CityFragment cityFragment3 = CityFragment.newInstance(2,"",CityListDialogFragment.this);

        mFragments.add(cityFragment1);
        mFragments.add(cityFragment2);
        mFragments.add(cityFragment3);

        mFragmentTitles.add("请选择");
        mFragmentTitles.add("请选择");
        mFragmentTitles.add("请选择");

    }

    private void setupViewPager(int page,ViewPager viewPager,String id,String name) {

        adapter.reset();

        if(page==0){

            mFragmentTitles.set(0,"请选择");
            adapter.addFragment(mFragments.get(0), mFragmentTitles.get(0));
            adapter.notifyDataSetChanged();
           // mFragments.get(0).getNewData(id);
        }

        if(page==1){
            mFragments.get(1).setCitycode(id);
            mFragmentTitles.set(0,name);
            mFragmentTitles.set(1,"请选择");
            adapter.addFragment(mFragments.get(0), mFragmentTitles.get(0));
            adapter.addFragment(mFragments.get(1), mFragmentTitles.get(1));
            adapter.notifyDataSetChanged();
            mFragments.get(1).getNewData(id);
        }

        if(page==2){
            mFragments.get(2).setCitycode(id);
            mFragmentTitles.set(1,name);
            mFragmentTitles.set(2,"请选择");
            adapter.addFragment(mFragments.get(0), mFragmentTitles.get(0));
            adapter.addFragment(mFragments.get(1), mFragmentTitles.get(1));
            adapter.addFragment(mFragments.get(2), mFragmentTitles.get(2));
            adapter.notifyDataSetChanged();
            mFragments.get(2).getNewData(id);
        }

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void removeFragment(int position){
            mFragments.remove(position);
            mFragmentTitles.remove(position);
        }

        public void setTitle(int position,String title){
            mFragmentTitles.set(position,title);
        }

        public void reset(){
            mFragmentTitles.clear();
            mFragments.clear();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


}
