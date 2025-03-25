package com.utc.asm_mob_java.screen.detailscreen;

import android.os.Bundle;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.databinding.DetailActivityBinding;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;

public class DetailActivity extends BaseBindingActivity<DetailActivityBinding, DetailPresenter> implements DetailView {

    @Override
    public void showMessage(String mess) {

    }

    @Override
    public void showLoading() {
        showLoadingDialog(false);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showErr(String err) {

    }

    @Override
    protected int getIdLayout() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initData() {
        mPresenter = new DetailPresenter(this, this);
        mBinding.setPresenter(mPresenter);
        mPresenter.loadLocalImages(mBinding.slider);
    }

    @Override
    public Product getProduct() {
        Bundle bundle = getIntent().getBundleExtra(Constants.BundleKey.ITEM);
        if (bundle != null) {
            String json = bundle.getString(Constants.BundleKey.ITEM);
            return GsonUtils.String2Object(json, Product.class);
        }
        return null;
    }
}
