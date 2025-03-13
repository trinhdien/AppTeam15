package com.utc.asm_mob_java.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.utc.asm_mob_java.R;

public class DialogUtils {
    private void showConfirmDialog(Context context, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, null, false);
        builder.setView(binding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
