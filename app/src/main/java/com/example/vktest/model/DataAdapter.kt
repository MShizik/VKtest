package com.example.vktest.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.example.vktest.R
import com.google.android.material.imageview.ShapeableImageView

class DataAdapter(private var context: Context, private var alGifDataModels: ArrayList<Pair<GifDataModel, GifDataModel?>>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var ivGifViewFirst: ImageView? = null
        var ivGifViewSecond: ImageView? = null

        init {
            this.ivGifViewFirst = row?.findViewById(R.id.imgViewCard)
            this.ivGifViewSecond = row?.findViewById(R.id.imgViewCardSecond)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.doubled_card_layout, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }


        var dmGifFirst = alGifDataModels[position].first
        var dmGifSecond : GifDataModel? = alGifDataModels[position].second

        Glide
            .with(context)
            .load(dmGifFirst.getImageUrl())
            .into(viewHolder.ivGifViewFirst!!)

        Glide
            .with(context)
            .load(dmGifSecond?.getImageUrl())
            .into(viewHolder.ivGifViewSecond!!)

        return view as View
    }

    override fun getItem(i: Int): Pair<GifDataModel,GifDataModel?> = alGifDataModels[i]


    override fun getItemId(i: Int): Long = i.toLong()


    override fun getCount(): Int = alGifDataModels.size

    fun updateResults(newResult : ArrayList<Pair<GifDataModel, GifDataModel?>>){
        alGifDataModels.clear()
        alGifDataModels.addAll(newResult)
        notifyDataSetChanged()
    }

}