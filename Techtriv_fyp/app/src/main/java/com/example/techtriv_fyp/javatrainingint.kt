package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.github.barteksc.pdfviewer.PDFView

class javatrainingint : AppCompatActivity() {
    private lateinit var backbtn : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_javatrainingint)
        val pdfView: PDFView = findViewById(R.id.javapdf)
        pdfView.fromAsset("javaint.pdf").load()

        backbtn = findViewById(R.id.backbtn)
        backbtn.setOnClickListener {

            val intent = Intent(this, java::class.java)
            startActivity(intent)
        }
    }
}