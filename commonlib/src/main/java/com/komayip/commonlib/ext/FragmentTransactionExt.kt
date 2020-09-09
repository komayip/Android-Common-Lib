package com.komayip.commonlib.ext

import androidx.fragment.app.FragmentTransaction
import com.komayip.commonlib.R

fun FragmentTransaction.slide(): FragmentTransaction {
    return this.setCustomAnimations(
        R.anim.enter_from_right,
        R.anim.exit_to_left,
        R.anim.enter_from_left,
        R.anim.exit_to_right
    )
}