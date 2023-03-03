package com.example.vktest.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vktest.R
import com.example.vktest.model.DataAdapter
import com.example.vktest.model.GifDataModel
import com.example.vktest.views.GifsListView
import com.example.vktest.views.InfoGifView
import java.util.ArrayList

class ActivityInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val viewGifView : InfoGifView = InfoGifView(window.decorView.rootView)

        val currentGif : GifDataModel = intent.extras?.get("currentGif") as GifDataModel

        viewGifView.setGif(currentGif.getImageUrl(), this)
        viewGifView.setImageData(stID = currentGif.getID(), stName = currentGif.getName(), stAuthor = currentGif.getAuthor())

        viewGifView.setOnClickListener{
            var intentBack = Intent(this, MainActivity::class.java)
            intentBack.putExtra("lastSearch", intent.extras!!.getString("lastSearch", ""))
            intentBack.putExtra("lastLocale", intent.extras!!.getString("lastLocale", ""))
            startActivity(intentBack)
        }

    }
}