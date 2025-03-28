package com.utc.asm_mob_java.screen.loginscreen;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.LoginActivityBinding;

public class LoginActivity extends BaseBindingActivity<LoginActivityBinding, LoginPresenter> implements LoginView {
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

    }

    @Override
    protected int getIdLayout() {
        return R.layout.login_activity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(LoginActivity.this, this);
        mBinding.setPresenter(mPresenter);
        mBinding.tvPass.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = mBinding.tvPass.getCompoundDrawablesRelative()[2];
                if (drawableEnd != null && event.getRawX() >= (mBinding.tvPass.getRight() - drawableEnd.getBounds().width() - mBinding.tvPass.getPaddingEnd())) {
                    mPresenter.isShowPass.set(!mPresenter.isShowPass.get());
                    mBinding.tvPass.setTransformationMethod(mPresenter.isShowPass.get() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    return true;
                }
            }
            return false;
        });

    }
}
