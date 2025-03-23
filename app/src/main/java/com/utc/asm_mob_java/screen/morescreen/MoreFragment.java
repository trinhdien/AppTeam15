package com.utc.asm_mob_java.screen.morescreen;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.MoreFragmentBinding;
import com.utc.asm_mob_java.dialog.dialogconfirm.ConfirmDialog;

public class MoreFragment extends BaseBindingFragment<MoreFragmentBinding, MorePresenter> implements MoreView {
    public static MoreFragment newInstance() {
        Bundle args = new Bundle();
        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getIdLayoutRes() {
        return R.layout.more_fragment;
    }

    @Override
    protected void initData() {
        mPresenter = new MorePresenter(mActivity, this);
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
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showErr() {
        ConfirmDialog confirmDialog = new ConfirmDialog(null, mActivity.getResources()
                .getString(R.string.error),
                mActivity.getResources().getString(R.string.error_get_province),
                true, "", mActivity.getResources().getString(R.string.ok));
        confirmDialog.show(mActivity.getSupportFragmentManager(), "");
    }

    @Override
    public void setImageBitMap(Bitmap bitmap) {
        mBinding.imgAvt.setImageBitmap(bitmap);
    }
}
