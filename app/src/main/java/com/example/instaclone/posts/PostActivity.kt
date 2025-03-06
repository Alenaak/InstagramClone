package com.example.instaclone.posts

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.activities.HomeActivity
import com.example.instaclone.databinding.ActivityPostBinding
import com.example.instaclone.models.Posts
import com.example.instaclone.models.User

import com.example.instaclone.utils.POST
import com.example.instaclone.utils.POSTS_FOLDER
import com.example.instaclone.utils.USER_NODE
import com.example.instaclone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
     val binding by lazy {
         ActivityPostBinding.inflate(layoutInflater)
     }

    private  val TAG = "PostActivity"
    private lateinit var imageurl:String
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri.let {
            if (uri != null) {
                uploadImage(uri, POSTS_FOLDER){
                    url->
                    if(it==null){

                    }else{
                        binding.imageView1.setImageURI(uri)
                        if (url != null) {
                            imageurl=url
                        }
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
        binding.imageView1.setOnClickListener {
            launcher.launch("image/*")
        }


       binding.cancel.setOnClickListener {
           startActivity(Intent(this, HomeActivity::class.java))
           finish()
       }
        binding.button3.setOnClickListener {
            Firebase.auth.currentUser?.let { it1 ->
                Firebase.firestore.collection(USER_NODE).document(it1.uid).get().addOnSuccessListener {
                    var user: User = it.toObject<User>()!!
                    val post = Posts(imageurl, caption =  binding.textInputLayout.editText?.text.toString(), uid=Firebase.auth.currentUser?.uid, time = System.currentTimeMillis().toString())

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.auth.currentUser?.let { it1 ->
                            Firebase.firestore.collection(it1.uid).document().set(post)
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