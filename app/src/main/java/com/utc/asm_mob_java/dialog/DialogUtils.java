package com.utc.asm_mob_java.dialog;

import android.content.Context;
import android.os.Handler;

import com.utc.asm_mob_java.utils.Logger;

public class DialogUtils {
    public static void showLoadingDialog(Context context, boolean cancelable) {
        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog dialogLoading = LoadingDialog.newInstance(context);
                    dialogLoading.setCancelable(cancelable);
                    dialogLoading.show();
                }
            });
        } catch (Exception e) {
            Logger.log(DialogUtils.class, e);
        }
    }

    public static void hideLoadingDialog(Context context) {
        try {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.newInstance(context).dismiss();

                }
            });
        } catch (Exception e) {
            Logger.log(DialogUtils.class, e);
        }


    }
}
