package com.utc.asm_mob_java.base.basefragment;

import androidx.fragment.app.Fragment;

import com.utc.asm_mob_java.base.baseactivity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    public void hideLoadingDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingDialog();
        }
    }

    public void showLoadingDialog(final boolean cancelable) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoadingDialog(cancelable);
        }
    }


}