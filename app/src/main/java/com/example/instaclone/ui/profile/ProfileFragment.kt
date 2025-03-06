package com.example.instaclone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instaclone.models.User
import com.example.instaclone.activities.SignUpActivity
import com.example.instaclone.adapters.ViewPagerAdapter
import com.example.instaclone.databinding.FragmentProfileBinding

import com.example.instaclone.utils.USER_NODE
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        viewPagerAdapter.addFragment(PostFragment(), "Posts")
        viewPagerAdapter.addFragment(ReelFragment(), "Reels")

        binding.viewPager.adapter = viewPagerAdapter

        // Since TabLayout does not support `setupWithViewPager()` for ViewPager2,
        // we need to manually link TabLayout with ViewPager2 using TabLayoutMediator.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Posts"
                1 -> "Reels"
                else -> ""
            }
        }.attach()

        binding.editProfile.setOnClickListener {
            val intent=Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        return root
    }


    override fun onStart() {
        super.onStart()
        Firebase.auth.currentUser?.let { it ->
            Firebase.firestore.collection(USER_NODE).document(it.uid).get().addOnSuccessListener {
                val user: User? = it.toObject<User>()
                if (user != null) {
                    binding.name.text = user.username
                }
                if (user != null) {
                    if (!user.photo.isNullOrEmpty()){
                       Picasso.get().load(user.photo).into(binding.profileImage)
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}