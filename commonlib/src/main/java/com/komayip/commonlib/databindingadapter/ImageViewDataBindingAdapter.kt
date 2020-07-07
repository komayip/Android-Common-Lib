package com.komayip.commonlib.databindingadapter

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import java.lang.Exception

class ImageViewDataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("android:src")
        fun setSrr(img: ImageView, @DrawableRes res: Int) {
            try {
                img.setImageResource(res)
            } catch (e: Exception) {
                img.setImageResource(0)
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setSrr(img: ImageView, bitmap: Bitmap?) {
            try {
                img.setImageBitmap(bitmap)
            } catch (e: Exception) {
                img.setImageResource(0)
            }
        }

    }

}