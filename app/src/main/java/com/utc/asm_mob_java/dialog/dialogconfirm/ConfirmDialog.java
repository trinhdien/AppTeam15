package com.utc.asm_mob_java.dialog.dialogconfirm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.utc.asm_mob_java.databinding.DialogConfirmBinding;

public class ConfirmDialog extends DialogFragment {
    private DialogConfirmBinding binding;
    private ConfirmListener listener;
    private String title;
    private String content;

    public ConfirmDialog(ConfirmListener listener, String title, String content) {
        this.listener = listener;
        this.title = title;
        this.content = content;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        binding = DialogConfirmBinding.inflate(getLayoutInflater());
        binding.setPresenter(this);
        if (title != null) {
            binding.tvTitle.setText(title);
        }
        if (content != null) {
            binding.tvContent.setText(content);
        }
        builder.setView(binding.getRoot());
        return builder.create();
    }

    public void onConfirmClick() {
        if (listener != null) listener.onConfirm();
        dismiss();
    }

    public void onCancelClick() {
        dismiss();
    }

    public interface ConfirmListener {
        void onConfirm();
    }
}
