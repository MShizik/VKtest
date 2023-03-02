package com.example.vktest.model;

import java.io.Serializable

public class GifDataModel( private var stName : String, private var stID : String,private var stAuthor:String, private var stImageUrl : String) : Serializable {

    fun getID(): String = stID

    fun getAuthor(): String = stAuthor

    fun getName() : String = stName

    fun getImageUrl() : String = this.stImageUrl

    fun setImageUrl(stNewUrl : String) { stImageUrl = stNewUrl}

}
