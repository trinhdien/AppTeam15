package com.utc.asm_mob_java.screen.cartscreen;

import android.os.Bundle;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.CartFragmentBinding;

public class CartFragment extends BaseBindingFragment<CartFragmentBinding, CartPresenter> implements CartView {
    public static CartFragment newInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getIdLayoutRes() {
        return R.layout.cart_fragment;
    }

    @Override
    protected void initData() {
        mPresenter = new CartPresenter(mActivity, this);
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
