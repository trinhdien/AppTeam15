package com.utc.asm_mob_java.screen.mainscreen;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;

public class MainScreenPresenter extends BasePresenterForm<MainScreenView> {
    public MainScreenPresenter(Context mContext, MainScreenView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        Toast.makeText(mActivity, "Đã Đăng nhập", Toast.LENGTH_SHORT).show();
    }
}
