package com.example.instaclone.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.databinding.PostshowfragmentdesignBinding
import com.example.instaclone.models.Posts
import com.example.instaclone.models.User
import com.example.instaclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

private const val TAG = "PostAdapter"

class PostAdapter(var context: Context, var postLists:ArrayList<Posts>) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: PostshowfragmentdesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= PostshowfragmentdesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postLists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      try{
          postLists[position].uid?.let {
              Firebase.firestore.collection(USER_NODE).document(it).get().addOnSuccessListener {
                  val user=it.toObject<User>()
                  if (user != null) {
                      Log.d(TAG, "onBindViewHolder: ${user.username} ${user.photo}")
                      Glide.with(context).load(user.photo).placeholder(R.drawable.profilephoto).into(holder.binding.profileImage)
                      holder.binding.name.text=user.username
                      Glide.with(context).load(postLists[position].posturl).placeholder(R.drawable.profilephoto).into(holder.binding.postimage)
                      try {
                         var time= postLists[position].time?.let { it1 -> TimeAgo.using(it1.toLong()) }
                          holder.binding.time.text=time
                      }catch (e:Exception){
                          holder.binding.time.text=""
                      }


                      holder.binding.postcaption.text=postLists[position].caption
                      holder.binding.likeButton.setOnClickListener{
                          holder.binding.likeButton.setImageResource(R.drawable.liked)
                      }
                      holder.binding.shareButton.setOnClickListener {
                          val i =Intent(android.content.Intent.ACTION_SEND)
                          i.type="text/plain"
                          i.putExtra(Intent.EXTRA_TEXT,postLists.get(position).posturl)
                          context.startActivity(i)

                      }
                  }
              }
          }
      }catch (e:Exception){

      }


    }
}