package com.utc.asm_mob_java.screen.loginscreen;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.LoginFragmentBinding;

public class LoginFragment extends BaseBindingFragment<LoginFragmentBinding, LoginPresenter> implements LoginView {
    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
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

    @Override
    protected int getIdLayoutRes() {
        return R.layout.login_fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(mActivity, this);
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

    @Override
    protected void initView() {

    }
}
