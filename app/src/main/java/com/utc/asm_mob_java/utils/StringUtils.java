package com.utc.asm_mob_java.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class StringUtils {
    public static Bitmap convertBase64ToBitmap(String base64String) {
        if (base64String.contains(",")) {
            base64String = base64String.split(",")[1];
        }
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
