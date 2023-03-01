package com.example.vktest.model;

public class GifDataModel( private var stName : String, private var stID : String,private var stAuthor:String, private var stImageUrl : String) {

    fun getID(): String = stID

    fun getAuthor(): String = stAuthor

    fun getName() : String = stName

    fun getImageUrl() : String = this.stImageUrl

    fun setImageUrl(stNewUrl : String) { stImageUrl = stNewUrl}

}
