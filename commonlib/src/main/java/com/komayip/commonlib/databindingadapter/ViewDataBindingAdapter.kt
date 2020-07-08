package com.komayip.commonlib.databindingadapter

import android.view.View
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

/**
 * Useful Common Data Binding Adapter For View Class
 */
class ViewDataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("isVisible")
        fun setVisible(v: View, isVisible: Boolean?) {
            v.visibility = if (isVisible == true) View.VISIBLE else View.INVISIBLE
        }

        @JvmStatic
        @BindingAdapter("isVisibleOrGone")
        fun setVisibleOrGone(v: View, isVisible: Boolean?) {
            v.visibility = if (isVisible == true) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("android:background")
        fun setBackground(v: View, @DrawableRes res: Int) {
            try {
                v.setBackgroundResource(res)
            } catch (e: Exception) {
                v.setBackgroundResource(res)
            }
        }

    }

}