package com.komayip.commonlib.widget

import androidx.databinding.ObservableField

/**
 * An ObservableField that will always emit a value even same as the previous value
 */
class NonDistinctObservableField<T> : ObservableField<T>() {

    override fun set(value: T) {
        if (get() == value) {
            notifyChange()
        } else {
            super.set(value)
        }
    }

}