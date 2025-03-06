package com.example.instaclone.ui.reels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.instaclone.adapters.ReelAdapter
import com.example.instaclone.databinding.FragmentReelsBinding
import com.example.instaclone.models.Reel
import com.example.instaclone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject



class ReelsFragment : Fragment() {

    private lateinit var binding: FragmentReelsBinding
    lateinit var adapter: ReelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentReelsBinding.inflate(inflater, container, false)
        var reelsList= ArrayList<Reel>()
        adapter= ReelAdapter(requireContext(), reelsList)
        binding.viewPager.adapter=adapter
        binding.viewPager.orientation=ViewPager2.ORIENTATION_VERTICAL
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList=ArrayList<Reel>()
            for(i in it.documents){
                var reel: Reel =i.toObject<Reel>()!!

                tempList.add(reel)

            }
            reelsList.addAll(tempList)
            reelsList.reverse()
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}