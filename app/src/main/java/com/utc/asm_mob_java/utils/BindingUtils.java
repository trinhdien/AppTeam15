package com.utc.asm_mob_java.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingUtils {
    @BindingAdapter("app:imageSrc")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
