package com.utc.asm_mob_java.dialog.dialogconfirm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.utc.asm_mob_java.databinding.DialogConfirmBinding;
import com.utc.asm_mob_java.dialog.BaseListener;

public class ConfirmDialog extends DialogFragment {
    private final BaseListener listener;
    private final String title;
    private final String content;
    private boolean isHideCancel;
    private String tvCancel;
    private String tvConfirm;

    public ConfirmDialog(BaseListener listener, String title, String content) {
        this.listener = listener;
        this.title = title;
        this.content = content;
    }

    public ConfirmDialog(BaseListener listener, String title, String content, boolean isHideCancel, String tvCancel, String tvConfirm) {
        this.listener = listener;
        this.title = title;
        this.content = content;
        this.isHideCancel = isHideCancel;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        DialogConfirmBinding binding = DialogConfirmBinding.inflate(getLayoutInflater());
        binding.setPresenter(this);
        builder.setView(binding.getRoot());
        if (title != null) {
            binding.tvTitle.setText(title);
        }
        if (content != null) {
            binding.tvContent.setText(content);
        }
        if (isHideCancel) {
            binding.comboBtn.setVisibility(View.GONE);
            binding.btnConfirm2.setVisibility(View.VISIBLE);
        }
        if (tvCancel != null) {
            binding.btnCancel.setText(tvCancel);
        }
        if (tvConfirm != null) {
            binding.btnConfirm.setText(tvConfirm);
            binding.btnConfirm2.setText(tvConfirm);
        }
        return builder.create();
    }

    public void onConfirmClick() {
        if (listener != null) listener.onConfirm();
        dismiss();
    }

    public void onCancelClick() {
        if (listener != null) listener.onCancel();
        dismiss();
    }
}
