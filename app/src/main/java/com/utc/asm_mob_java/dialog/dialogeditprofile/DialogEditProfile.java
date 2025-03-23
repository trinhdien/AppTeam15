package com.utc.asm_mob_java.dialog.dialogeditprofile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.DialogFragment;

import com.utc.asm_mob_java.databinding.DialogEditProfileBinding;


public class DialogEditProfile extends DialogFragment {
    public ObservableField<String> name;
    public ObservableField<String> email;
    public ObservableField<String> numberPhone;
    public ObservableField<String> birthday;
    private final EditProfileListener listener;

    public DialogEditProfile(EditProfileListener listener, String name, String email, String numberPhone, String birthday) {
        this.name = new ObservableField<>(name);
        this.email = new ObservableField<>(email);
        this.numberPhone = new ObservableField<>(numberPhone);
        this.birthday = new ObservableField<>(birthday);
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        DialogEditProfileBinding binding = DialogEditProfileBinding.inflate(getLayoutInflater());
        binding.setPresenter(this);
        builder.setView(binding.getRoot());
        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });
        return builder.create();
    }

    public void onConfirmClick() {
        if (listener != null)
            listener.onConfirm(name.get(), email.get(), numberPhone.get(), birthday.get());
        dismiss();
    }

    public void onCancelClick() {
        if (listener != null) listener.onCancel();
        dismiss();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view.clearFocus();
    }
}