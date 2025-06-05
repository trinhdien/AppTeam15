package com.utc.asm_mob_java.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {
    public static Bitmap convertBase64ToBitmap(String base64String) {
        if (base64String.contains(",")) {
            base64String = base64String.split(",")[1];
        }
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String removeDiacritics(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", ""); // Loại bỏ dấu
    }

    public static boolean isMatch(String input, String target) {
        String inputNormalized = removeDiacritics(input.toLowerCase());
        String targetNormalized = removeDiacritics(target.toLowerCase());
        return targetNormalized.contains(inputNormalized);
    }

    //Email phải có định dạng username@domain.com
    //Chỉ chứa ký tự chữ, số, dấu . _ % + -
    //Tên miền hợp lệ với 2-6 ký tự cuối (.com, .vn, .net, ...)
    public static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailPattern);
    }

    //Bắt đầu bằng +84 hoặc 0
    //Đầu số hợp lệ: 03, 05, 07, 08, 09 (các mạng di động Việt Nam)
    //Tổng cộng 10 số
    public static boolean isValidVietnamPhoneNumber(String phone) {
        String phonePattern = "^(\\+84|0)[3|5|7|8|9][0-9]{8}$";
        return phone == null || !phone.matches(phonePattern);
    }

    //Độ dài từ 5 đến 20 ký tự Chỉ chứa chữ cái, số, dấu _
    public static boolean isValidUsername(String username) {
        String usernamePattern = "^[a-zA-Z0-9_]{5,20}$";
        return username == null || !username.matches(usernamePattern);
    }

    //Ít nhất 1 chữ cái (A-Z, a-z)
    //Ít nhất 1 số (0-9)
    //Ít nhất 1 ký tự đặc biệt (@, $, !, %, *, ?, &)
    //Độ dài từ 8 đến 20 ký tự
    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return password != null && password.matches(passwordPattern);
    }

    public static String normalize(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

    @SuppressLint("DefaultLocale")
    public static String formatIntToPrice(int price){
        NumberFormat nf = NumberFormat.getInstance(Locale.US); // Dùng dấu phẩy (,)
        return nf.format(price);
    }
    public static String privateNumberPhone(String numberPhone) {
        if(CommonActivity.isNullOrEmpty(numberPhone)){
            return "";
        }
        if (numberPhone.length() <= 6) {
            return "*".repeat(numberPhone.length());
        }
        String hienThi = numberPhone.substring(0, numberPhone.length() - 6);
        return hienThi + "******";
    }

}
