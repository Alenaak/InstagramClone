package com.example.instaclone.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.adapters.SearchAdapter
import com.example.instaclone.databinding.FragmentSearchBinding
import com.example.instaclone.models.User
import com.example.instaclone.utils.USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class SearchFragment : Fragment() {
    private val TAG = "SearchFragment"
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private val userList = ArrayList<User>()
    private val firestore = FirebaseFirestore.getInstance()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        adapter = SearchAdapter(requireContext(), userList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Set SearchView Listener (Moved outside button click)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    Log.d(TAG, "onQueryTextSubmit: Searching for $query")
                    Toast.makeText(context, "Searching for: $query", Toast.LENGTH_SHORT).show()

                    // Query Firestore
                    firestore.collection(USER_NODE)
                        .whereEqualTo("username", query)
                        .get()
                        .addOnSuccessListener { result ->
                            val tempList = ArrayList<User>()
                            userList.clear()

                            if (result.isEmpty) {
                                Log.d(TAG, "No results found for: $query")
                            } else {
                                for (document in result.documents) {
                                    if (document.id != currentUserId) {
                                        val user = document.toObject<User>()
                                        if (user != null) tempList.add(user)
                                    }
                                }
                                userList.addAll(tempList)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error fetching search results", e)
                        }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return binding.root
    }
}
