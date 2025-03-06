package com.example.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.models.User
import com.example.instaclone.databinding.ActivitySignUpBinding
import com.example.instaclone.utils.PROFILE_FOLDER
import com.example.instaclone.utils.USER_NODE
import com.example.instaclone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {

    private  val TAG = "SignUpActivity"
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri.let {
            if (uri != null) {
                uploadImage(uri, PROFILE_FOLDER){
                    if(it==null){

                    } else{
                        user.photo= it
                        binding.profileImage.setImageURI(uri)
                    }
                }
            }

        }
    }
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        user=User()
        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE",-1)==1){
                binding.Register.text="Update Profile"
                binding.already.visibility=View.GONE
                binding.login.visibility=View.GONE
                Firebase.auth.currentUser?.let { it ->
                    Firebase.firestore.collection(USER_NODE).document(it.uid).get().addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if (!user.photo.isNullOrEmpty()){
                            Picasso.get().load(user.photo).into(binding.profileImage)
                        }
                        binding.email.editText?.setText(user.email)
                        binding.username.editText?.setText(user.username)
                        binding.password.editText?.setText(user.password)

                    }
                }
            }
        }
        binding.addImage.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.Register.setOnClickListener {

            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE",-1)==1){
                    FirebaseAuth.getInstance().currentUser?.let { it1 ->
                        Firebase.firestore.collection(USER_NODE).document(
                            it1.uid).set(user).addOnSuccessListener {  }
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }



                }

            }
            else{
                if( binding.email.editText?.text.toString().equals("") || binding.username.editText?.text.toString().equals("") || binding.password.editText?.text.toString().equals("") ){
                    Log.d(
                        TAG, "Values: " +
                                binding.email.editText?.text.toString() +
                                binding.username.editText?.text.toString() +
                                binding.password.editText?.text.toString()
                    )
                    Toast.makeText(this,"Please Fill Complete Information", Toast.LENGTH_SHORT).show()
                }
                else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.email.editText?.text.toString(), binding.password.editText?.text.toString()).addOnCompleteListener {result ->
                        if(result.isSuccessful){
                            user.email=  binding.email.editText?.text.toString()
                            user.username= binding.username.editText?.text.toString()
                            user.password= binding.password.editText?.text.toString()
                            Firebase.auth.currentUser?.let { it1 ->
                                Firebase.firestore.collection(USER_NODE).document(
                                    it1.uid).set(user).addOnSuccessListener{
                                    Toast.makeText(applicationContext,"Valid",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, HomeActivity::class.java))
                                    finish()
                                }
                            }
                        }else{
                            Toast.makeText(this,
                                result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            }

        }
    }
}