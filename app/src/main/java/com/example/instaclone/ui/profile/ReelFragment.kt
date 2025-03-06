package com.example.instaclone.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instaclone.adapters.MyReelAdapter
import com.example.instaclone.databinding.FragmentReelBinding
import com.example.instaclone.models.Posts
import com.example.instaclone.models.Reel
import com.example.instaclone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects


class ReelFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding
    private lateinit var adapter: MyReelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private  val TAG = "PostFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate(inflater,container,false)
        var reelsList= ArrayList<Reel>()
        adapter= MyReelAdapter(requireContext(), reelsList)
        binding.recyclerview.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerview.adapter=adapter
        Firebase.auth.currentUser?.let {
            Firebase.firestore.collection(it.uid+ REEL).get().addOnSuccessListener {
                var tempList= arrayListOf<Reel>()

                for (i in it.documents){
                    var reel: Reel = i.toObject<Reel>()!!
                    tempList.add(reel)
                    Log.d(TAG, "onCreateView: templist:$tempList")


                }

                reelsList.addAll(tempList)
                Log.d(TAG, "onCreateView: $reelsList")
                adapter.notifyDataSetChanged()

            }

        }
        return binding.root
    }

    companion object {

    }
}