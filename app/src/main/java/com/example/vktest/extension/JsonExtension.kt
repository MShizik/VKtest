package com.example.vktest.extension;

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

fun JSONObject.str(key: String, default: String = "") : String {
    return if (has(key))  getString(key) else default
}

fun JSONArray.jsonObject(index: Int) : JSONObject {
    return getJSONObject(index)
}

fun JSONObject.array(key: String, default: JSONArray = JSONArray()) : JSONArray {
    return if (has(key)) getJSONArray(key) else default
}