package com.example.instaclone.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaclone.databinding.FragmentAddBinding
import com.example.instaclone.posts.PostActivity
import com.example.instaclone.posts.ReelActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.image.setOnClickListener {
            activity?.startActivity(Intent(requireActivity(),PostActivity::class.java))
        }
        binding.video.setOnClickListener {
            activity?.startActivity(Intent(requireActivity(), ReelActivity::class.java))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}