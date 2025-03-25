package com.utc.asm_mob_java.screen.registerscreen;

import android.util.Log;
import android.widget.Toast;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.RegisterFragmentBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;

public class RegisterFragment extends BaseBindingFragment<RegisterFragmentBinding, RegisterPresenter> implements RegisterView {
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void showMessage(String mess) {
        Toast.makeText(mActivity, mess, Toast.LENGTH_SHORT).show();
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
        DialogUtils.showErrDialog(mActivity, mActivity.getResources().getString(R.string.error_get_province)).show(mActivity.getSupportFragmentManager(), "");
    }

    @Override
    protected int getIdLayoutRes() {
        return R.layout.register_fragment;
    }

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = new RegisterPresenter(mActivity, this);
        }
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        Log.d("zzzz", "onDestroy: ");
        super.onDestroy();
    }
}
