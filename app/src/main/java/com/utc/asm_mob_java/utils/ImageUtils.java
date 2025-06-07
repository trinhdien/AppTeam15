package com.utc.asm_mob_java.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class ImageUtils {
    public static Bitmap convertBase64ToBitmap(String base64String) {
        if (base64String.contains(",")) {
            base64String = base64String.split(",")[1];
        }
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convertImageToBase64(AppCompatActivity activity, Uri imageUri) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            byte[] imageBytes = outputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(StringUtils.class.getName(), Objects.requireNonNull(e.getMessage()));
            return null;
        }
    }
}
