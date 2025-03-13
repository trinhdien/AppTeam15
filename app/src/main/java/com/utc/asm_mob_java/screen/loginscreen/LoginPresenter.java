package com.utc.asm_mob_java.screen.loginscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.gson.reflect.TypeToken;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.screen.mainscreen.MainScreenActivity;
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
        if (CommonActivity.isNullOrEmpty(username.get()) || CommonActivity.isNullOrEmpty(password.get())) {
            Toast.makeText(mActivity, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        for (User user : mListUser) {
            if (Objects.requireNonNull(username.get()).equalsIgnoreCase(user.getUsername()) && Objects.requireNonNull(password.get()).equalsIgnoreCase(user.getPassword())) {
                String userString = GsonUtils.Object2String(user);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
                gotoMainScreen(userString);
            }
        }
    }

    private void fakeData() {
        if (!CommonActivity.isNullOrEmpty(mSharedPrefManager.getListUser())) {
            mListUser = GsonUtils.String2ListObject(mSharedPrefManager.getListUser(), User[].class);
        } else {
            List<DeliveryAddress> address = new ArrayList<>();
            address.add(new DeliveryAddress("Đắc Trí, Định Bình, Yên Định Thanh Hoá", true));
            mListUser.add(new User("Buddy", "buddy@gnail.com", "0987654321", "03/09/2003", address, "buddy", "buddy1", Constants.IMAGE_DEFAULT));
            mSharedPrefManager.saveListUser(GsonUtils.Object2String(mListUser));
        }


    }

    private void gotoMainScreen(String user) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.USER, user);
        Intent intent = new Intent(mActivity, MainScreenActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
