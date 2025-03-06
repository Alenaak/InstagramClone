package com.example.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.models.User
import com.example.instaclone.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var user: User
    private lateinit var email:String
    private lateinit var password:String
    private  val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        user= User()
        email=binding.email.editText?.text.toString()
        password=binding.password.editText?.text.toString()
        binding.login.setOnClickListener {
            if(email.equals("") || password.equals("")){
                Log.d(TAG, "onCreate: Fields are Empty")
                Toast.makeText(this,"Please fill all the details.",Toast.LENGTH_SHORT).show()
            }
            else{
                user=User(email,password)
                user.email?.let { it1 ->
                    user.password?.let { it2 ->
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(it1,
                            it2
                        ).addOnCompleteListener { result->
                            if(result.isSuccessful){
                                Log.d(TAG, "onCreate: SUCCESS")
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            } else{
                                Log.d(TAG, "onCreate: FAILED")
                            }
                        }
                    }
                }
            }

        }

        binding.createAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

    }
}