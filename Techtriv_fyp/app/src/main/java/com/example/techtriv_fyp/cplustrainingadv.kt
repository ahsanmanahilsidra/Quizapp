package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.github.barteksc.pdfviewer.PDFView

class cplustrainingadv : AppCompatActivity() {
    private lateinit var backbtn : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cplustrainingadv)
        val pdfView: PDFView = findViewById(R.id.cpluspdf)
        pdfView.fromAsset("cplusadv.pdf").load()
        backbtn = findViewById(R.id.backbtn)
        backbtn.setOnClickListener {

            val intent = Intent(this, cplus::class.java)
            startActivity(intent)
        }
    }
}