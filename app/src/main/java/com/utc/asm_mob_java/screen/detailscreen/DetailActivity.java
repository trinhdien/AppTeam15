package com.utc.asm_mob_java.screen.detailscreen;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.DetailActivityBinding;

public class DetailActivity extends BaseBindingActivity<DetailActivityBinding, DetailPresenter> implements DetailView {
    @Override
    public void showMessage() {

    }

    @Override
    public void showLoading() {
        showLoading();
    }

    @Override
    public void hideLoading() {
        hideLoading();
    }

    @Override
    public void showErr() {

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
}
