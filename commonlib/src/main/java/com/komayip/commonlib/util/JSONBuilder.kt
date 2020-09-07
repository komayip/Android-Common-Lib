package com.komayip.commonlib.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

/**
 * Tools for generating JSON / JSON String / Request Body
 */
open class JSONBuilder {

    private val gson = Gson()

    private var map: HashMap<String, Any?> = HashMap()

    open fun append(key: String, value: Any?): JSONBuilder {
        try {
            map[key] = value?: JSONObject.NULL
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun buildJSON(): JsonObject {
        return gson.toJsonTree(map).asJsonObject
    }

    fun buildJSONString(): String {
        return gson.toJson(map)
    }

    fun buildRequestBody(type: String = "application/json"): RequestBody {
        return RequestBody.create(MediaType.parse(type), buildJSONString())
    }

}