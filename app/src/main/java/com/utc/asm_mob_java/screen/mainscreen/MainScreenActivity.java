package com.utc.asm_mob_java.screen.mainscreen;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.MainScreenActivityBinding;
import com.utc.asm_mob_java.dialog.BaseListener;
import com.utc.asm_mob_java.dialog.dialogconfirm.ConfirmDialog;
import com.utc.asm_mob_java.screen.cartscreen.CartFragment;
import com.utc.asm_mob_java.screen.historyscreen.HistoryFragment;
import com.utc.asm_mob_java.screen.homescreen.HomeFragment;
import com.utc.asm_mob_java.screen.morescreen.MoreFragment;
import com.utc.asm_mob_java.utils.Common;

public class MainScreenActivity extends BaseBindingActivity<MainScreenActivityBinding, MainScreenPresenter> implements MainScreenView {
    int currentFragment = R.id.nav_home;
    private BottomNavigationView bottomNavigationView;

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
        bottomNavigationView = mBinding.bottomNavigation;
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
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                Fragment fragmentAddress = getSupportFragmentManager().findFragmentById(R.id.frame_main);

                if (fragmentAddress != null && fragmentAddress.isVisible()) {
                    fragmentTransaction.remove(fragmentAddress).commit();
                } else {
                    if (currentFragment != R.id.nav_home) {
                        Common.replaceFragment(MainScreenActivity.this, R.id.container, HomeFragment.newInstance());
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);
                    } else {
                        BaseListener listener = new BaseListener() {
                            @Override
                            public void onConfirm() {
                                super.onConfirm();
                            }
                        };
                        ConfirmDialog dialog = new ConfirmDialog(listener, getResources().getString(R.string.warning), getResources().getString(R.string.do_you_want_exit_app));
                        dialog.show(getSupportFragmentManager(), "");
                    }
                }
            }
        });
    }

}
