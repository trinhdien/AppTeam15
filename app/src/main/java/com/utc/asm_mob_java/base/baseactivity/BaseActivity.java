package com.utc.asm_mob_java.base.baseactivity;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.utc.asm_mob_java.dialog.LoadingDialog;
import com.utc.asm_mob_java.utils.Logger;

public abstract class BaseActivity extends AppCompatActivity {
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
    }

    protected abstract int getIdLayout();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showLoadingDialog(final boolean cancelable) {
        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                            mLoadingDialog.dismiss();
                            mLoadingDialog = null;
                        }

                        if (mLoadingDialog == null) {
                            mLoadingDialog = new LoadingDialog(BaseActivity.this);
                        }
                        mLoadingDialog.show();

                    } catch (Exception e) {
                        Logger.log(this.getClass(), e);
                    }


                }
            });
        } catch (Exception e) {
            Logger.log(this.getClass(), e);
        }


    }

    public void hideLoadingDialog() {
        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
                            return;
                        }
                        mLoadingDialog.dismiss();
                    } catch (Exception e) {
                        Logger.log(this.getClass(), e);
                    }

                }
            });
        } catch (Exception e) {
            Logger.log(this.getClass(), e);
        }


    }
}
