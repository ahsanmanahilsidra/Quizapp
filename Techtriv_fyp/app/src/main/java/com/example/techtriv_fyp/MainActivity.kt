package com.example.techtriv_fyp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoView= findViewById<VideoView>(R.id.splashscreen)
        val videoPath="android.resource://"+packageName+"/"+R.raw.splash_screen

        val videoUri= Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)
        videoView.start()

        videoView.setOnCompletionListener {
            if (FirebaseAuth.getInstance().currentUser==null)
            {
            val intent= Intent(this, Login::class.java)
            startActivity(intent)}
            else{
                val intent= Intent(this, Instruction::class.java)
                startActivity(intent)}
            finish()
            }

        }
    }