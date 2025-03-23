package com.utc.asm_mob_java.dialog.dialogfilter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.DialogFragment;

import com.utc.asm_mob_java.databinding.DialogFilterBinding;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogFilter<T> extends DialogFragment {
    public ObservableBoolean isNoData;
    private final DialogFilterListener<T> listener;
    public ObservableField<String> mTitle;
    private final List<T> mList;
    private final List<T> mListTemp;
    private DialogFilterBinding mBinding;
    private DialogFilterAdapter<T> mAdapter;

    public DialogFilter(DialogFilterListener<T> listener, String mTitle, List<T> mList) {
        this.mTitle = new ObservableField<>(mTitle);
        this.listener = listener;
        this.mList = mList;
        mListTemp = new ArrayList<>(mList);
        isNoData = new ObservableBoolean(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogFilterBinding.inflate(inflater, container, false);
        mBinding.setPresenter(this);
        return mBinding.getRoot();
    }

    public void onCancelClick() {
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new DialogFilterAdapter<>(mList, getContext());
        mBinding.adapter.setAdapter(mAdapter);
        mAdapter.setListener((item, position) -> {
            if (getDialog() != null && getDialog().getCurrentFocus() != null
                    && getDialog().getCurrentFocus() == mBinding.search) {
                hideKeyboard();
                return;
            }
            if (listener != null) {
                listener.onChoose(item, position);
                dismiss();
            }
        });
        if (CommonActivity.isNullOrEmpty(mList)) {
            isNoData.set(true);
        }
        mBinding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
        mBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable) {
                if (CommonActivity.isNullOrEmpty(editable.toString())) {
                    mList.clear();
                    mList.addAll(mListTemp);
                } else {
                    mList.clear();
                    for (T item : mListTemp) {
                        if (StringUtils.isMatch(editable.toString(), item.toString())) {
                            mList.add(item);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
                isNoData.set(CommonActivity.isNullOrEmpty(mList));
            }
        });
        mBinding.noData.setOnClickListener(view1 -> hideKeyboard());
    }

    private void hideKeyboard() {
        View view = Objects.requireNonNull(getDialog()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            view.clearFocus();
        }
    }
}
