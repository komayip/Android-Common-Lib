package com.komayip.commonlib.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

/**
 * A builder for building item decoration with margin for recyclerview
 */
class MarginItemDecorationBuilder(val context: Context) {

    private val mDecor = MarginItemDecoration()

    private val mRes = context.resources

    /**
     * set the span count
     */
    fun setSpanCount(spanCount: Int): MarginItemDecorationBuilder {
        this.mDecor.spanCount = spanCount
        return this
    }

    fun setMargin(margin: Int): MarginItemDecorationBuilder {
        return this.setMarginStart(margin)
            .setMarginEnd(margin)
            .setMarginTop(margin)
            .setMarginBottom(margin)
    }

    fun setMarginRes(@DimenRes margin: Int): MarginItemDecorationBuilder {
        return this.setMarginStartRes(margin)
            .setMarginEndRes(margin)
            .setMarginTopRes(margin)
            .setMarginBottomRes(margin)
    }

    fun setMarginStart(margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginStart = margin
        return this
    }

    fun setMarginStartRes(@DimenRes margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginStart = mRes.getDimensionPixelSize(margin)
        return this
    }

    fun setMarginEnd(margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginEnd = margin
        return this
    }

    fun setMarginEndRes(@DimenRes margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginEnd = mRes.getDimensionPixelSize(margin)
        return this
    }

    fun setMarginTop(margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginTop = margin
        return this
    }

    fun setMarginTopRes(@DimenRes margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginTop = mRes.getDimensionPixelSize(margin)
        return this
    }

    fun setMarginBottom(margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginBottom = margin
        return this
    }

    fun setMarginBottomRes(@DimenRes margin: Int): MarginItemDecorationBuilder {
        this.mDecor.marginBottom = mRes.getDimensionPixelSize(margin)
        return this
    }

    fun setIncludeStartEnd(include: Boolean): MarginItemDecorationBuilder {
        return this.setIncludeStart(true)
            .setIncludeEnd(true)
    }

    fun setIncludeStart(include: Boolean): MarginItemDecorationBuilder {
        this.mDecor.includeStart = true
        return this
    }

    fun setIncludeEnd(include: Boolean): MarginItemDecorationBuilder {
        this.mDecor.includeEnd = true
        return this
    }

    fun build(): MarginItemDecoration {
        return mDecor
    }
}

/**
 * the margin item decoration
 */
class MarginItemDecoration : RecyclerView.ItemDecoration() {

    /**
     * the span count, 1 = linear, n = grid
     */
    var spanCount = 1

    /**
     * the margin start
     */
    var marginStart = 0

    /**
     * the margin end
     */
    var marginEnd = 0

    /**
     * the margin top
     */
    var marginTop = 0

    /**
     * the margin bottom
     */
    var marginBottom = 0

    /**
     * should include margin start
     */
    var includeStart = false

    /**
     * should include margin end
     */
    var includeEnd = false

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeStart) {
            outRect.left = marginStart
        }

        if (includeEnd) {
            outRect.right = marginEnd
        }

        if (position >= spanCount) { // top edge
            outRect.top = marginTop
        }

        outRect.bottom = marginBottom // item bottom
    }
}