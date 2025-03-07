package com.utc.asm_mob_java.screen.homescreen;

import android.os.Bundle;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.HomeFragmentBinding;

public class HomeFragment extends BaseBindingFragment<HomeFragmentBinding, HomePresenter> implements HomeView {
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getIdLayoutRes() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initData() {
        mPresenter = new HomePresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showMessage() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErr() {

    }
}
