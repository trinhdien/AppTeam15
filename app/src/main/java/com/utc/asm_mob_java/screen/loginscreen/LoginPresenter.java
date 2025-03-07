package com.utc.asm_mob_java.screen.loginscreen;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.screen.mainscreen.MainScreenActivity;

public class LoginPresenter extends BasePresenterForm<LoginView> {
    public ObservableBoolean isShowPass;
    public ObservableField<String> username;
    public ObservableField<String> password;
    public LoginPresenter(Context mContext, LoginView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        isShowPass = new ObservableBoolean(false);
        username = new ObservableField<>("");
        password = new ObservableField<>("");
    }

    public void onLogin() {
        Intent intent = new Intent(mActivity, MainScreenActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
