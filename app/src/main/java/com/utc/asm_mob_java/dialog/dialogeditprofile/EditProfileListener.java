package com.utc.asm_mob_java.dialog.dialogeditprofile;

public interface EditProfileListener {
    void onConfirm(String name, String email, String numberPhone, String birthday);
    void onCancel();
}
