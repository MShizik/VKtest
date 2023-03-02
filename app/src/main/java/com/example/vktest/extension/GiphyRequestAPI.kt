package com.example.vktest.extension

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyRequestAPI {

    @GET("v1/gifs/search?api_key=SjWz5FP3lbG3lBoOBV9xNZSUPGODu1zr")
    fun getGifs(@Query("q") query : String, @Query("offset") offset : Int, @Query("limit") limit : Int,@Query("rating") rating : String , @Query("lang") lang : String)  : Call<ResponseBody>
}