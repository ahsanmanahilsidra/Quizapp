package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.techtriv_fyp.databinding.ActivityHomePageBinding
import com.github.barteksc.pdfviewer.PDFView

class cplustrainingbeg : AppCompatActivity() {
    private lateinit var backbtn : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cplustrainingbeg)
        val pdfView: PDFView = findViewById(R.id.cpluspdf)
        pdfView.fromAsset("cplusbeg.pdf").load()
        backbtn = findViewById(R.id.backbtn)
        backbtn.setOnClickListener {

            val intent = Intent(this, cplus::class.java)
            startActivity(intent)
        }

    }

}