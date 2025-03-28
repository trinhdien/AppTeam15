package com.utc.asm_mob_java.screen.registerscreen;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Toast;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.RegisterFragmentBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;

public class RegisterFragment extends BaseBindingFragment<RegisterFragmentBinding, RegisterPresenter> implements RegisterView {
    RegisterCallBack callBack;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = new RegisterPresenter(mActivity, this);
        }
        mBinding.setPresenter(mPresenter);
        mBinding.tvPass.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = mBinding.tvPass.getCompoundDrawablesRelative()[2];
                if (drawableEnd != null && event.getRawX() >= (mBinding.tvPass.getRight() - drawableEnd.getBounds().width() - mBinding.tvPass.getPaddingEnd())) {
                    mPresenter.isShowPass.set(Boolean.FALSE.equals(mPresenter.isShowPass.get()));
                    mBinding.tvPass.setTransformationMethod(Boolean.TRUE.equals(mPresenter.isShowPass.get()) ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    return true;
                }
            }
            return false;
        });
        mBinding.tvPassAgain.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = mBinding.tvPassAgain.getCompoundDrawablesRelative()[2];
                if (drawableEnd != null && event.getRawX() >= (mBinding.tvPassAgain.getRight() - drawableEnd.getBounds().width() - mBinding.tvPassAgain.getPaddingEnd())) {
                    mPresenter.isShowPassAgain.set(Boolean.FALSE.equals(mPresenter.isShowPassAgain.get()));
                    mBinding.tvPassAgain.setTransformationMethod(Boolean.TRUE.equals(mPresenter.isShowPassAgain.get()) ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    protected void initView() {
        hideLoadingDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setCallBack(RegisterCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onRegisterSuccess() {
        if (callBack != null) {
            callBack.onRegister();
            mActivity.getOnBackPressedDispatcher().onBackPressed();
        }
    }
}
