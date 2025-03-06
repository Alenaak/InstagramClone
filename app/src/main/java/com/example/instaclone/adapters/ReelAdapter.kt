package com.example.instaclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.ReelfragmentdesignBinding
import com.example.instaclone.models.Reel
import com.squareup.picasso.Picasso

class ReelAdapter(var context: Context, var videoList:ArrayList<Reel>): RecyclerView.Adapter<ReelAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ReelfragmentdesignBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelAdapter.ViewHolder {
        var binding= ReelfragmentdesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      Picasso.get().load(videoList[position].profileLink).placeholder(R.drawable.profilephoto).into(holder.binding.profileImage)
      holder.binding.caption.setText(videoList[position].caption)
      holder.binding.videoView.setVideoPath(videoList[position].reelurl)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progressBar.visibility=View.GONE
            holder.binding.videoView.start()
        }
    }



    override fun getItemCount(): Int {
        return videoList.size
    }

}