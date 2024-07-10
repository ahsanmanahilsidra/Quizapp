package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class pystbg : AppCompatActivity() {
    private lateinit var startbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pystbg)
        startbtn= findViewById(R.id.btnstart)
        startbtn.setOnClickListener {

            val intent = Intent(this,pyquizbgg ::class.java)
            startActivity(intent)
        }
    }
}