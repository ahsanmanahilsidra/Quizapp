package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Instruction : AppCompatActivity() {
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)
        loginButton = findViewById(R.id.bnext)
        loginButton.setOnClickListener {

            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
}