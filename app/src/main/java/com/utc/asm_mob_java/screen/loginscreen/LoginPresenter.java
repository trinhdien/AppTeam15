package com.utc.asm_mob_java.screen.loginscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.screen.mainscreen.MainScreenActivity;
import com.utc.asm_mob_java.screen.registerscreen.RegisterCallBack;
import com.utc.asm_mob_java.screen.registerscreen.RegisterFragment;
import com.utc.asm_mob_java.utils.Common;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginPresenter extends BasePresenterForm<LoginView> {
    public ObservableBoolean isShowPass;
    public ObservableField<String> username;
    public ObservableField<String> password;
    private List<User> mListUser;
    private SharedPrefManager mSharedPrefManager;

    public LoginPresenter(Context mContext, LoginView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        isShowPass = new ObservableBoolean(false);
        username = new ObservableField<>("");
        password = new ObservableField<>("");
        mSharedPrefManager = new SharedPrefManager(mActivity);
        mListUser = new ArrayList<>();
        fakeData();
    }

    public void onLogin() {
        if (CommonActivity.isNullOrEmpty(username.get())) {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.please_enter_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if (CommonActivity.isNullOrEmpty(username.get())) {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.please_enter_pass), Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkLogin() != null) {
            String userString = GsonUtils.Object2String(checkLogin());
            mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(checkLogin()));
            gotoMainScreen(userString);
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.dont_match_pass_or_username), Toast.LENGTH_SHORT).show();
        }
    }

    private void fakeData() {
        if (!CommonActivity.isNullOrEmpty(mSharedPrefManager.getListUser())) {
            mListUser = GsonUtils.String2ListObject(mSharedPrefManager.getListUser(), User[].class);
        }
    }

    private void gotoMainScreen(String user) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.USER, user);
        Intent intent = new Intent(mActivity, MainScreenActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    private User checkLogin() {
        for (User user : mListUser) {
            if (Objects.requireNonNull(username.get()).equalsIgnoreCase(user.getUsername()) && Objects.requireNonNull(password.get()).equalsIgnoreCase(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public void onClickRegister() {
        mView.showLoading();
        RegisterCallBack callBack = this::fakeData;
        RegisterFragment fragment = RegisterFragment.newInstance();
        fragment.setCallBack(callBack);
        Common.replaceFragment(mActivity, R.id.main, fragment);
    }

    public void onClickForgotPass() {

    }
}
