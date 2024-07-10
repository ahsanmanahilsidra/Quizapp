package com.example.techtriv_fyp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.techtriv_fyp.databinding.ActivityHtmlquizBinding

class htmlquiz : AppCompatActivity() {
    val binding by
    lazy {
        ActivityHtmlquizBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}