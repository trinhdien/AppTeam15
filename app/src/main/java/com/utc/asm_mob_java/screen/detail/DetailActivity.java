package com.utc.asm_mob_java.screen.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.ActivityDetailBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.ImageUtils;

public class DetailActivity extends BaseBindingActivity<ActivityDetailBinding,DetailPresenter> implements DetailView{
    @Override
    public void showMessage(String mess) {

    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showErr(String err) {
        DialogUtils.showErrDialog(this,err);
    }

    @Override
    protected int getIdLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData() {
        mPresenter = new DetailPresenter(this,this);
        mBinding.setPresenter(mPresenter);
        Bundle bundle = getIntent().getBundleExtra(Constants.BundleKey.ITEM);
        if(bundle != null){
            if(bundle.containsKey(Constants.BundleKey.INT) &&  mPresenter != null){
                mPresenter.getDetailNews(String.valueOf(bundle.getInt(Constants.BundleKey.INT)));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.compositeDisposable.dispose();
        }
    }

    @Override
    public void setImage(String base64) {
        Bitmap bitmap = ImageUtils.convertBase64ToBitmap(base64);
        mBinding.propertyImage.setImageBitmap(bitmap);
    }
}
