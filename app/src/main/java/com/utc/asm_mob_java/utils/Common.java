package com.utc.asm_mob_java.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Common {
    public static void replaceFragment(AppCompatActivity mActivity, int fmActivity, Fragment fm) {

        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        fragmentTransaction.replace(fmActivity, fm);
        if (!mActivity.getSupportFragmentManager().isStateSaved()) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public static void replaceAndKillFragment(AppCompatActivity mActivity, int fmActivity, Fragment fm) {

        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        Fragment currentFragment = fragmentManager.findFragmentById(fmActivity);
        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }
        transaction.replace(fmActivity, fm);
        transaction.commit();
    }
}
