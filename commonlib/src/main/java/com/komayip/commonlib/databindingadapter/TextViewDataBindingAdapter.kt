package com.komayip.commonlib.databindingadapter

import android.graphics.Paint
import android.text.Html
import android.text.TextUtils
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.komayip.commonlib.R

/**
 * Useful Common Data Binding Adapter For TextView Class
 */
class TextViewDataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(tv: TextView, @StringRes res: Int) {
            try {
                tv.text = tv.context.getString(res)
            } catch (e: Exception) {
                tv.text = ""
            }
        }

        @JvmStatic
        @BindingAdapter("android:textSize")
        fun setTextSize(tv: TextView, @DimenRes res: Int) {
            try {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.context.resources.getDimension(res))
            } catch (e: Exception) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,tv.context.resources.getDimension(R.dimen.medium))
            }
        }

        @JvmStatic
        @BindingAdapter("htmlString")
        fun setHtmlString(tv: TextView, s: String) {
            if (!TextUtils.isEmpty(s)) {
                tv.text = Html.fromHtml(s)
            } else {
                tv.text = ""
            }
        }

        @JvmStatic
        @BindingAdapter("htmlString")
        fun setHtmlString(tv: TextView, @StringRes res: Int) {
            if (res == 0) {
                return
            }

            val s = tv.context.getString(res)
            setHtmlString(tv, s)
        }

        @BindingAdapter(value = ["android:text", "underline"], requireAll = true)
        @JvmStatic
        fun setUnderlineText(tv: TextView, s: String?, underline: Boolean?) {
            tv.text = s
            underline?.let {
                if (it) {
                    tv.paintFlags = tv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                } else {
                    tv.paintFlags = tv.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:textColor")
        fun setColor(tv: TextView, @ColorInt res: Int) {
            try {
                tv.setTextColor(res)
            } catch (e: Exception) { }
        }

        @JvmStatic
        @BindingAdapter("android:textColor")
        fun setColorRes(tv: TextView, @ColorRes res: Int) {
            try {
                tv.setTextColor(ContextCompat.getColor(tv.context, res))
            } catch (e: Exception) { }
        }

    }

}