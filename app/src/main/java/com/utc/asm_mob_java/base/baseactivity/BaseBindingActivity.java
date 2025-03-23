package com.utc.asm_mob_java.base.baseactivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBindingActivity<T extends ViewDataBinding, K> extends BaseActivity {
    protected T mBinding;
    protected K mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getIdLayout());
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null && mPresenter instanceof BasePresenterForm) {
            ((BasePresenterForm<?>) mPresenter).unSubscription();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).subscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).unSubscribe();
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    hideKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    // Hàm ẩn bàn phím
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }
}
