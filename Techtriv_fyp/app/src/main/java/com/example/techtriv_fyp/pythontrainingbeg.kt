package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.tabs.TabLayout

class pythontrainingbeg : AppCompatActivity() {
    private lateinit var backbtn : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pythontrainingbeg)
        val pdfView: PDFView = findViewById(R.id.pythonpdf)
        pdfView.fromAsset("pybeg.pdf").load()
        backbtn = findViewById(R.id.backbtn)
        backbtn.setOnClickListener {

            val intent = Intent(this, python::class.java)
            startActivity(intent)
        }
    }

}