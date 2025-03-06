package com.example.instaclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instaclone.databinding.ReelDesignBinding
import com.example.instaclone.models.Reel

class MyReelAdapter(var context: Context,var videoList:ArrayList<Reel>): RecyclerView.Adapter<MyReelAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ReelDesignBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReelAdapter.ViewHolder {
        var binding=ReelDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyReelAdapter.ViewHolder, position: Int) {
       Glide.with(context).load(videoList[position].reelurl)
           .diskCacheStrategy(DiskCacheStrategy.ALL)
           .into(holder.binding.postImage)
    }

    override fun getItemCount(): Int {
       return videoList.size
    }

}