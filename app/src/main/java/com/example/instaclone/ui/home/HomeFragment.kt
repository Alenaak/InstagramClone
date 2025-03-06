package com.example.instaclone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.R
import com.example.instaclone.adapters.FollowAdapter
import com.example.instaclone.adapters.PostAdapter
import com.example.instaclone.adapters.ReelAdapter
import com.example.instaclone.databinding.FragmentHomeBinding
import com.example.instaclone.models.Posts
import com.example.instaclone.models.Reel
import com.example.instaclone.models.User
import com.example.instaclone.utils.FOLLOW
import com.example.instaclone.utils.POST
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {

   private lateinit var binding: FragmentHomeBinding
//
   lateinit var adapter: PostAdapter
    lateinit var adapter2: FollowAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar)
        var postsList= ArrayList<Posts>()
        var followList=ArrayList<User>()


        adapter2=FollowAdapter(requireContext(),followList)
        binding.recyclerViewstory.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewstory.adapter=adapter2


        adapter= PostAdapter(requireContext(), postsList)
        binding.recyclerView1.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView1.adapter=adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get().addOnSuccessListener {
            var tempList1=ArrayList<User>()
            followList.clear()
            for (i in it.documents){
                var friends:User=i.toObject<User>()!!
                tempList1.add(friends)

            }
            followList.addAll(tempList1)
            adapter2.notifyDataSetChanged()

        }




        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList=ArrayList<Posts>()
            postsList.clear()
            for (i in it.documents){
                var post:Posts=i.toObject<Posts>()!!
                tempList.add(post)

            }
            postsList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}