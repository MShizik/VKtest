package com.example.vktest.views

import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.vktest.R
import com.example.vktest.model.DataAdapter

class GifsListView(var rootView : View){

    private var lvGifsList : ListView = rootView.findViewById(R.id.main_lv)

    private var pbGifsLoader : ProgressBar = rootView.findViewById(R.id.main_progress_bar)

    private var etSearch : EditText = rootView.findViewById(R.id.main_search_et)

    fun getSearchText() : String{
        return etSearch.text.toString()
    }

    fun getSearchET(): EditText = etSearch

    fun getGifsList() : ListView = lvGifsList

    fun setDataAdapter(adapter : DataAdapter){
        lvGifsList.adapter  = adapter
    }

    fun setStartPosition(){
        lvGifsList.visibility = View.VISIBLE
        lvGifsList.alpha = 0F
        pbGifsLoader.visibility = View.VISIBLE
        pbGifsLoader.alpha = 1F
    }

    fun startLoadingAnimation(){
        pbGifsLoader.animate().setDuration(2500L).alpha(1F).setListener(null)
    }

    fun endLoadingAnimation(){
        pbGifsLoader.animate().setDuration(1500L).alpha(0F).withEndAction{pbGifsLoader.visibility = View.GONE}
        lvGifsList.animate().setDuration(2500L).alpha(1.0F).setListener(null)
    }
}