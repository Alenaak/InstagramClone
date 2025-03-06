package com.example.instaclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.databinding.SearchrecyclerviewBinding
import com.example.instaclone.models.User
import com.example.instaclone.utils.FOLLOW
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SearchAdapter(var context: Context,var userList:ArrayList<User>):RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SearchrecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=SearchrecyclerviewBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isfollow=false
        holder.binding.username.text=userList[position].username
        Glide.with(context).load(userList[position].photo).placeholder(R.drawable.profilephoto).into(holder.binding.profileImage)
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList[position].email).get().addOnSuccessListener {
            if (it.documents.size==0){
                   isfollow=false
            }else{
                holder.binding.followButton.text="Unfollow"
                isfollow=true
            }
        }


        holder.binding.followButton.setOnClickListener {
            if(isfollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList[position].email).get().addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                  holder.binding.followButton.text="follow"
                    isfollow=false
                }
            }else{
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document().set(userList.get(position))
                holder.binding.followButton.text="Unfollow"
                isfollow=true
            }

            }


        }



}