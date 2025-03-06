package com.example.instaclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.PostDesignBinding
import android.content.Context
import com.example.instaclone.models.Posts
import com.squareup.picasso.Picasso

class MyPostAdapter(var context:Context,var postLists:ArrayList<Posts>) : RecyclerView.Adapter<MyPostAdapter.ViewHolder>(){
    inner class ViewHolder(var binding:PostDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding=PostDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postLists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(postLists[position].posturl).into(holder.binding.postImage)
    }
}