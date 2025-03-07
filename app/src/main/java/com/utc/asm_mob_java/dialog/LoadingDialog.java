package com.utc.asm_mob_java.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.databinding.DialogLoadingBinding;

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.DialogLoading);
    }

    public static LoadingDialog newInstance(Context context) {
        return new LoadingDialog(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogLoadingBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_loading, null, true);
        setContentView(mBinding.getRoot());
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mBinding.setDialog(this);
    }


}