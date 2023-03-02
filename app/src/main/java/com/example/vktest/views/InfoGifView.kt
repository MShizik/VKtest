package com.example.vktest.views

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.vktest.R

class InfoGifView(rootView : View){

    private val ivGifView : ImageView = rootView.findViewById(R.id.info_img_view)

    private val tvImageNameHolder : TextView = rootView.findViewById(R.id.info_name_tv)

    private val tvImageIdValueHolder : TextView = rootView.findViewById(R.id.info_id_value_tv)
    private val tvImageAuthorValueHolder : TextView = rootView.findViewById(R.id.info_author_value_tv)

    private val btnBack : ImageButton = rootView.findViewById(R.id.info_back_btn)

    fun setGif(url : String, context : Context){
        Glide
            .with(context)
            .load(url)
            .into(ivGifView)
    }

    fun setImageData(stName : String, stID : String, stAuthor : String){
        tvImageNameHolder.text = stName
        tvImageIdValueHolder.text = stID
        tvImageAuthorValueHolder.text = stAuthor
    }

    fun setOnClickListener(onClickListener: View.OnClickListener){
        btnBack.setOnClickListener(onClickListener)
    }

}