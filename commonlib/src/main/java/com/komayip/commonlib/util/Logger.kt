package com.komayip.commonlib.util

import android.util.Log
import androidx.databinding.library.BuildConfig
import com.google.gson.GsonBuilder

/**
 * Logging Tools
 */
class Logger(name: String?) {

    private val tag = "${javaClass.simpleName}:$name"

    fun debugLog(s: String?) {
        debugLog(s, null)
    }

    fun debugLog(s: String?, dec: String?) {
        var s = s
        try {
            if (isRelease()) {
                return
            }
            if (s == null) {
                s = "null"
            }
            if (dec != null) {
                Log.d("Logger", "$tag  $dec:\n$s")
            } else {
                Log.d("Logger", "$tag  $s")
            }
            Log.d(tag, "\n--------------------------------------------\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun debugLog(o: Any, dec: String?) {
        try {
            if (isRelease()) {
                return
            }
            try {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val msg = if (dec.isNullOrEmpty()) {
                    "${o.javaClass.name}:${gson.toJson(o)}"
                } else {
                    "$dec:\n${o.javaClass.name}:${gson.toJson(o)}"
                }

                Log.d("Logger", "$tag $msg")
                Log.d("Logger", "\n--------------------------------------------\n")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun debugLog(o: Any) {
        debugLog(o, null)
    }

    fun i(msg: String?) {
        if (isRelease()) {
            return
        }
        Log.i(tag, "" + msg)
    }

    fun d(msg: String?) {
        if (isRelease()) {
            return
        }
        Log.d(tag, "" + msg)
    }

    fun w(msg: String?) {
        if (isRelease()) {
            return
        }
        Log.w(tag, "" + msg)
    }

    fun e(msg: String?) {
        if (isRelease()) {
            return
        }
        Log.e(tag, "" + msg)
    }

    companion object {
        private fun isRelease(): Boolean {
            return BuildConfig.BUILD_TYPE.equals("release", true)
        }

        operator fun get(tag: String?): Logger {
            return Logger(tag)
        }

        fun get(): Logger? {
            return Logger("")
        }

        fun i(tag: String?, msg: String?) {
            if (isRelease()) {
                return
            }
            get(tag).i(msg)
        }

        fun d(tag: String?, msg: String?) {
            if (isRelease()) {
                return
            }
            get(tag).d(msg)
        }

        fun w(tag: String?, msg: String?) {
            if (isRelease()) {
                return
            }
            get(tag).w(msg)
        }

        fun e(tag: String?, msg: String?) {
            if (isRelease()) {
                return
            }
            get(tag).e(msg)
        }
    }

}