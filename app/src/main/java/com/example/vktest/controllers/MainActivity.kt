package com.example.vktest.controllers

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.vktest.R
import com.example.vktest.extension.*
import com.example.vktest.model.DataAdapter
import com.example.vktest.model.DataParser
import com.example.vktest.model.GifDataModel
import com.example.vktest.views.GifsListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.await
import java.util.*


class MainActivity : AppCompatActivity() {

    var timer : Timer? = null

    val BASE_URL = "https://api.giphy.com/"





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewGifsView : GifsListView = GifsListView(window.decorView.rootView)

        val alGifDataModels : ArrayList<Pair<GifDataModel, GifDataModel?>> = ArrayList()

        val adapter : DataAdapter = DataAdapter(this, alGifDataModels)
        viewGifsView.setDataAdapter(adapter)





        viewGifsView.getSearchET().addTextChangedListener(
            afterTextChanged = {

                val stLocale = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).currentInputMethodSubtype.toString()

                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        if (viewGifsView.getSearchText() == "") return
                        GlobalScope.launch(Dispatchers.Main){
                            alGifDataModels.clear()
                            viewGifsView.setStartPosition()
                            DataParser.parseJson(requestToApi(viewGifsView.getSearchText(), alGifDataModels.size, 25, "g", stLocale).string(),alGifDataModels)
                            if (alGifDataModels.size == 0){
                                viewGifsView.sayAboutError(this@MainActivity.resources.getString(R.string.error_cant_find_it))
                            }else{
                                viewGifsView.startLoadingAnimation()

                                adapter.notifyDataSetChanged()

                                viewGifsView.endLoadingAnimation()
                            }
                            WindowCompat.getInsetsController(window, window.decorView)?.hide(
                                WindowInsetsCompat.Type.ime())
                        }
                    }
                }, 600)
            },
            onTextChanged = {s, start, before, count->
            },
            beforeTextChanged = {s, start, before, count->
                timer?.cancel()
            }
        )
    }



    suspend fun requestToApi(stUserQuery : String, iOffset : Int, iLimit : Int, stRating : String, stLanguage : String) : ResponseBody = Retrofit.Builder()
                                                        .baseUrl(BASE_URL)
                                                        .build()
                                                        .create(GiphyRequestAPI::class.java)
                                                        .getGifs(stUserQuery, iOffset,iLimit,stRating,stLanguage)
                                                        .await()


}