package com.example.instaclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.databinding.StorylayoutBinding
import com.example.instaclone.models.User

class FollowAdapter(var context: Context, var followList:ArrayList<User>): RecyclerView.Adapter<FollowAdapter.ViewHolder>()  {
    inner class ViewHolder(var binding: StorylayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAdapter.ViewHolder {
        val binding=StorylayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowAdapter.ViewHolder, position: Int) {
      Glide.with(context).load(followList[position].photo).placeholder(R.drawable.profilephoto).into(holder.binding.storyImage)
        holder.binding.storyUsername.text=followList[position].username

    }

    override fun getItemCount(): Int {
        return followList.size
    }
}