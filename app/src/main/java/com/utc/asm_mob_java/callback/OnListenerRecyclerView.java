package com.utc.asm_mob_java.callback;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public abstract class OnListenerRecyclerView<T> {

    public void onClickItem(T item, int position) {

    }

    public void onClickItem1(T item, int position) {

    }

    public void onClickItemCheckSerial(T item, int position, View v, boolean b) {

    }

    public void onClickItem2(T item, int position) {

    }

    public void onClickItem3(T item, int position, View v, boolean b) {

    }

    public void onClickItem4(T item, int position) {

    }

    public void onClickButton(T item, int position) {

    }

    public void onCheckItem(T item, int position) {

    }

    public void onUncheckItem(T item, int position) {

    }

    public void onFocusChange(T item, EditText editText, boolean hasFocus) {
    }
    public void afterTextChanged(T item, int position, Editable editable){}
    public void onClickEdit(T item, int position) {

    }
    public void onClickDelete(T item, int position) {

    }
}
