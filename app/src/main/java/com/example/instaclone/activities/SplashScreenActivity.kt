package com.example.instaclone.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.R
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)


        window.statusBarColor=Color.TRANSPARENT


        Handler(Looper.getMainLooper()).postDelayed({
             if(FirebaseAuth.getInstance().currentUser==null){

                 startActivity(Intent(this, SignUpActivity::class.java))
                 finish()
             }
            else{
                 startActivity(Intent(this, HomeActivity::class.java))
                 finish()
            }


        },3000)
    }
}