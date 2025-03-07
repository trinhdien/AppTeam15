package com.utc.asm_mob_java.screen.mainscreen;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.MainScreenActivityBinding;
import com.utc.asm_mob_java.screen.cartscreen.CartFragment;
import com.utc.asm_mob_java.screen.historyscreen.HistoryFragment;
import com.utc.asm_mob_java.screen.homescreen.HomeFragment;
import com.utc.asm_mob_java.screen.morescreen.MoreFragment;
import com.utc.asm_mob_java.utils.Common;

public class MainScreenActivity extends BaseBindingActivity<MainScreenActivityBinding, MainScreenPresenter> implements MainScreenView {
    int currentFragment = R.id.nav_home;

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
    protected int getIdLayout() {
        return R.layout.main_screen_activity;
    }

    @Override
    protected void initData() {
        mPresenter = new MainScreenPresenter(this, this);
        mBinding.setPresenter(mPresenter);
        Common.replaceFragment(this, R.id.container, HomeFragment.newInstance());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == currentFragment) {
                return false;
            }
            currentFragment = item.getItemId();
            if (item.getItemId() == R.id.nav_home) {
                Common.replaceFragment(this, R.id.container, HomeFragment.newInstance());
                return true;
            } else if (item.getItemId() == R.id.nav_cart) {
                Common.replaceFragment(this, R.id.container, CartFragment.newInstance());
                return true;
            } else if (item.getItemId() == R.id.nav_history) {
                Common.replaceFragment(this, R.id.container, HistoryFragment.newInstance());
                return true;
            } else if (item.getItemId() == R.id.nav_more) {
                Common.replaceFragment(this, R.id.container, MoreFragment.newInstance());
                return true;
            }
            return false;
        });
    }
}
