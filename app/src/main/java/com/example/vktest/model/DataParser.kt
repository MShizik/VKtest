package com.example.vktest.model

import com.example.vktest.extension.array
import com.example.vktest.extension.jsonObject
import com.example.vktest.extension.str
import org.json.JSONObject

object DataParser {
    fun parseJson(JSON: String, alDataModels :  ArrayList<Pair<GifDataModel, GifDataModel?>>){
        var arrayJSON = JSONObject(JSON).array("data")
        var firstGif : GifDataModel? = null
        var secondGif : GifDataModel? = null
        for (i in 0 until arrayJSON.length()){
            var jsobjCurrent = arrayJSON.jsonObject(i)
            var filledGif = GifDataModel(
                jsobjCurrent.str("title"),
                jsobjCurrent.str("id"),
                jsobjCurrent.str("username"),
                jsobjCurrent.getJSONObject("images").getJSONObject("original").str("url"))
            if (firstGif == null){
                firstGif = filledGif
                secondGif = null
            }
            else{
                secondGif = filledGif
                alDataModels.add(Pair(firstGif, secondGif))
                firstGif = null
            }
        }
        if(firstGif != null) alDataModels.add(Pair(firstGif, null))
    }
}