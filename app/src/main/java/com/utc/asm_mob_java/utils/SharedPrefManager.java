package com.utc.asm_mob_java.utils;

import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_IS_LOGGED_IN;
import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_LIST_PRODUCT;
import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_PASSWORD;
import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_USERNAME;
import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_LIST_USER;
import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_PREF_NAME;
import static com.utc.asm_mob_java.utils.Constants.KEYSharedPref.KEY_USER_LOGIN;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Ghi dữ liệu
    public void saveListUser(String lstUser) {
        editor.putString(KEY_LIST_USER, lstUser);
        editor.apply();
    }

    public void saveUserLogin(String userLogin) {
        editor.putString(KEY_USER_LOGIN, userLogin);
        editor.apply();

    }

    public void saveListProduct(String lstProduct) {
        editor.putString(KEY_LIST_PRODUCT, lstProduct);
        editor.apply();

    }

    // Đọc dữ liệu
    public String getListUser() {
        return sharedPreferences.getString(KEY_LIST_USER, null);
    }

    public String getUserLogin() {
        return sharedPreferences.getString(KEY_USER_LOGIN, null);
    }

    public String getListProduct() {
        return sharedPreferences.getString(KEY_LIST_PRODUCT, null);

    }

    // Xóa dữ liệu
    public void clearData() {
        editor.clear();
        editor.apply();
    }
}
