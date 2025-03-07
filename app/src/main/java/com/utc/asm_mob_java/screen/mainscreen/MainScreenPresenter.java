package com.utc.asm_mob_java.screen.mainscreen;

import android.content.Context;
import android.view.MenuItem;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;

public class MainScreenPresenter extends BasePresenterForm<MainScreenView> {
    public ObservableField<Integer> selectedItem =  new ObservableField<>(R.id.nav_home);

    public MainScreenPresenter(Context mContext, MainScreenView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
    }

    public boolean onItemSelected(MenuItem item) {
        selectedItem.set(item.getItemId());
        return true;
    }
}
