package com.utc.asm_mob_java.dialog;

import android.content.Context;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.dialog.dialogconfirm.ConfirmDialog;

public class DialogUtils {
    public static ConfirmDialog showErrDialog(Context context, String err) {
        return new ConfirmDialog(null, context.getResources()
                .getString(R.string.error), err,
                true, "", context.getResources().getString(R.string.ok));
    }

    public static ConfirmDialog showConfirmDialog(BaseListener confirmListener, Context context, String title, String mess) {
        return new ConfirmDialog(confirmListener, title == null ? context.getResources().getString(R.string.confirm) : title, mess);
    }
}
