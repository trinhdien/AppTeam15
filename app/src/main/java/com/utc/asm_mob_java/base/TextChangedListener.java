package com.utc.asm_mob_java.base;

import android.text.Editable;
import android.text.TextWatcher;

public class TextChangedListener implements TextWatcher {
    private final OnTextChangeCallback callback;

    public TextChangedListener(OnTextChangeCallback callback) {
        this.callback = callback;
    }

    public interface OnTextChangeCallback {
        void onTextChanged(String newText);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (callback != null) {
            callback.onTextChanged(editable.toString());
        }
    }
}
