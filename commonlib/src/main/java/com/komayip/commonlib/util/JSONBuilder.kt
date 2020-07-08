package com.komayip.commonlib.util

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

/**
 * Tools for generating JSON / JSON String / Request Body
 */
open class JSONBuilder {

    private var jsonObject: JSONObject = JSONObject()

    open fun append(key: String, value: Any?): JSONBuilder {
        try {
            jsonObject.put(key, value?: JSONObject.NULL)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun buildJSON(): JSONObject {
        return jsonObject
    }

    fun buildJSONString(): String {
        return jsonObject.toString()
    }

    fun buildRequestBody(type: String = "application/json"): RequestBody {
        return RequestBody.create(MediaType.parse(type), buildJSONString())
    }

}