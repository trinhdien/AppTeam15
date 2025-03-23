package com.utc.asm_mob_java.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.text.Normalizer;

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
}
