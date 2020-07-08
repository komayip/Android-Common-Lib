package com.komayip.commonlib.widget

import androidx.databinding.ObservableInt

/**
 * same as ObservableInt,
 * only it will always notify changes even the source are the same
 */
class NonDistinctObservableInt: ObservableInt() {

    override fun set(value: Int) {
        if (get() == value) {
            notifyChange()
        } else {
            super.set(value)
        }
    }

}