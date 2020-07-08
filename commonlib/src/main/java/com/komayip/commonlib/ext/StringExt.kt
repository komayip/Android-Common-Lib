package com.komayip.commonlib.ext

import java.net.URLEncoder

fun String.toUtf8(): String {
    return URLEncoder.encode(this, "utf-8")
}

fun String?.orDefault(default: String): String {
    return if (this.isNullOrEmpty()) {
        default
    } else {
        this
    }
}