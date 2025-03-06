package com.example.instaclone.posts

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.activities.HomeActivity
import com.example.instaclone.databinding.ActivityReelBinding
import com.example.instaclone.models.Reel
import com.example.instaclone.models.User
import com.example.instaclone.utils.REEL
import com.example.instaclone.utils.REELS_FOLDER
import com.example.instaclone.utils.USER_NODE
import com.example.instaclone.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelActivity : AppCompatActivity() {
    private val binding by lazy { ActivityReelBinding.inflate(layoutInflater) }
    private lateinit var videourl:String
    lateinit var progressDialog:ProgressDialog
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri.let {
            if (uri != null) {
                uploadVideo(uri, REELS_FOLDER,progressDialog,this) { url ->
                    if (url != null) {
                        videourl = url
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressDialog=ProgressDialog(this)
        binding.cancel2.setOnClickListener {
            startActivity(Intent(this@ReelActivity, HomeActivity::class.java))
            finish()
        }
        binding.reel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.button3.setOnClickListener {
            Firebase.auth.currentUser?.let { it1 ->
                Firebase.firestore.collection(USER_NODE).document(it1.uid).get().addOnSuccessListener {
                    var user: User = it.toObject<User>()!!
                    val reel = Reel(videourl, binding.textInputLayout.editText?.text.toString(),
                        user.photo)

                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Firebase.auth.currentUser?.let { it1 ->
                            Firebase.firestore.collection(it1.uid + REEL).document().set(reel)
                                .addOnSuccessListener {
                                    startActivity(Intent(this, HomeActivity::class.java))
                                    finish()
                                }


                        }
                    }
                }
            }


        }
    }

    // Handle back button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back when back icon is clicked
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    }
