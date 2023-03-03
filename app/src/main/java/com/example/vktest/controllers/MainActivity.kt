package com.example.vktest.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
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

    private var iLastTouchPositionX : Int = 0

    private var USER_DISPLAY_WIDTH : Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewGifsView : GifsListView = GifsListView(window.decorView.rootView)

        val alGifDataModels : ArrayList<Pair<GifDataModel, GifDataModel?>> = ArrayList()

        val adapter : DataAdapter = DataAdapter(this, alGifDataModels)
        viewGifsView.setDataAdapter(adapter)

        var stLastSearch: String = ""
        var stLastLocale: String = ""


        if(intent.extras != null) {
            stLastSearch = intent.extras!!.getString("lastSearch", "")
            stLastLocale = intent.extras!!.getString("lastLocale", "")
        }

        if (stLastSearch != "" ||  stLastLocale != "") {
            viewGifsView.setSearchText(stLastSearch)
            GlobalScope.launch(Dispatchers.Main){
                alGifDataModels.clear()
                viewGifsView.setStartPosition()
                DataParser.parseJson(requestToApi(stLastSearch, alGifDataModels.size, 25, "g", stLastLocale).string(),alGifDataModels)
                if (alGifDataModels.size == 0){
                    viewGifsView.sayAboutError(this@MainActivity.resources.getString(R.string.error_cant_find_it))
                }else{
                    viewGifsView.startLoadingAnimation()

                    adapter.notifyDataSetChanged()

                    viewGifsView.endLoadingAnimation()
                }
                WindowCompat.getInsetsController(window, window.decorView).hide(
                    WindowInsetsCompat.Type.ime())
            }
        }
        else{
            viewGifsView.showGreeting()
        }


        USER_DISPLAY_WIDTH = DisplayMetrics().widthPixels

        viewGifsView.getSearchET().addTextChangedListener(
            afterTextChanged = {

                stLastLocale = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).currentInputMethodSubtype.toString()

                timer = Timer()

                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        stLastSearch = viewGifsView.getSearchText()
                        if (stLastSearch == "") return
                        GlobalScope.launch(Dispatchers.Main){
                            alGifDataModels.clear()
                            viewGifsView.setStartPosition()
                            DataParser.parseJson(requestToApi(stLastSearch, alGifDataModels.size, 25, "g", stLastLocale).string(),alGifDataModels)
                            if (alGifDataModels.size == 0){
                                viewGifsView.sayAboutError(this@MainActivity.resources.getString(R.string.error_cant_find_it))
                            }else{
                                viewGifsView.startLoadingAnimation()

                                adapter.notifyDataSetChanged()

                                viewGifsView.endLoadingAnimation()
                            }
                            WindowCompat.getInsetsController(window, window.decorView).hide(
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

        viewGifsView.getGifsList().setOnTouchListener { p0, p1 ->
            iLastTouchPositionX = p1?.x!!.toInt()
            false
        }

        viewGifsView.getGifsList().setOnItemClickListener { adapterView, view, i, l ->
            var element = if (iLastTouchPositionX  <USER_DISPLAY_WIDTH / 2) alGifDataModels[i].first else alGifDataModels[i].second
            var intent : Intent = Intent(this, ActivityInfo :: class.java)
            intent.putExtra("currentGif", element )
            intent.putExtra("lastSearch", stLastSearch)
            intent.putExtra("lastElement", stLastLocale)
            startActivity(intent)
        }
    }



    suspend fun requestToApi(stUserQuery : String, iOffset : Int, iLimit : Int, stRating : String, stLanguage : String) : ResponseBody = Retrofit.Builder()
                                                        .baseUrl(BASE_URL)
                                                        .build()
                                                        .create(GiphyRequestAPI::class.java)
                                                        .getGifs(stUserQuery, iOffset,iLimit,stRating,stLanguage)
                                                        .await()


}
