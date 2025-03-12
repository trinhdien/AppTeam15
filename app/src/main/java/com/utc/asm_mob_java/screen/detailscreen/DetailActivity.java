package com.utc.asm_mob_java.screen.detailscreen;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.DetailActivityBinding;

public class DetailActivity extends BaseBindingActivity<DetailActivityBinding, DetailPresenter> implements DetailView {
    private BottomSheetBehavior<View> bottomSheetBehavior;

    @Override
    public void showMessage() {

    }

    @Override
    public void showLoading() {
        showLoading();
    }

    @Override
    public void hideLoading() {
        hideLoading();
    }

    @Override
    public void showErr() {

    }

    @Override
    protected int getIdLayout() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initData() {
        mPresenter = new DetailPresenter(this, this);
        mBinding.setPresenter(mPresenter);
        mPresenter.loadLocalImages(mBinding.slider);

        bottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                bottomSheet.post(() -> {
                    bottomSheet.requestLayout();
                    bottomSheet.invalidate();
                });
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void showBottomSheet() {
        int behaviorState = bottomSheetBehavior.getState();
        if (behaviorState != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void closeBottomSheet() {
        int behaviorState = bottomSheetBehavior.getState();
        if (behaviorState != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}
