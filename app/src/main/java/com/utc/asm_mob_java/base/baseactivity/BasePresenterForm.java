package com.utc.asm_mob_java.base.baseactivity;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BasePresenterForm<T> {
    protected Context mContext;
    protected AppCompatActivity mActivity;
    protected T mView;

    public BasePresenterForm(Context mContext, T mView) {
        this.mContext = mContext;
        this.mView = mView;
        mActivity = ((AppCompatActivity) mContext);
        initData();
    }

    protected abstract void initData();

    public void onCancel() {

    }

    public void unSubscription() {

    }
}
