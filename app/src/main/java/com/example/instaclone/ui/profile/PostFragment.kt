package com.example.instaclone.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instaclone.R
import com.example.instaclone.adapters.MyPostAdapter
import com.example.instaclone.databinding.FragmentAddBinding
import com.example.instaclone.databinding.FragmentPostBinding
import com.example.instaclone.models.Posts
import com.example.instaclone.utils.POST
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects


class PostFragment : Fragment() {
     private lateinit var binding: FragmentPostBinding
    private lateinit var adapter: MyPostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private  val TAG = "PostFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPostBinding.inflate(inflater,container,false)
        var postsList= ArrayList<Posts>()
        adapter= MyPostAdapter(requireContext(), postsList)
        binding.recyclerview.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerview.adapter=adapter
        Firebase.auth.currentUser?.let {
            Firebase.firestore.collection(it.uid).get().addOnSuccessListener {
                    var tempList= arrayListOf<Posts>()

                for (i in it.documents){
                        var post: Posts = i.toObject<Posts>()!!
                        tempList.add(post)
                        Log.d(TAG, "onCreateView: templist:$tempList")


                }

                postsList.addAll(tempList)
                Log.d(TAG, "onCreateView: $postsList")
                adapter.notifyDataSetChanged()

            }

        }
        return binding.root
    }

    companion object {

    }
}