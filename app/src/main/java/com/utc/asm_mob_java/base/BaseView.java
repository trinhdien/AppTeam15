package com.utc.asm_mob_java.base;

public interface BaseView<T> {
    void showMessage(String mess);
    void showLoading();
    void hideLoading();
    void showErr(String err);
}
