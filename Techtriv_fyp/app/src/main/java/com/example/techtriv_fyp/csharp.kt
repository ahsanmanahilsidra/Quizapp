package com.example.techtriv_fyp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import com.example.techtriv_fyp.databinding.ActivityCBinding
import com.example.techtriv_fyp.databinding.ActivityCsharpBinding
import com.example.techtriv_fyp.databinding.ActivityPythonBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class csharp : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var videoUri: Uri
    private var currentPosition = 0
    val binding by lazy {
        ActivityCsharpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.name.text = it.data!!["name"].toString()

                }
            }
        setupVideoView()
        binding.trainingbegll.setOnClickListener(){
            val intent = Intent(this, csharptrainingbeg::class.java)
            startActivity(intent)
        }
        binding.trainingadvll.setOnClickListener(){
            val intent = Intent(this, csharptrainingadv::class.java)
            startActivity(intent)
        }
        binding.traininginterll.setOnClickListener(){
            val intent = Intent(this, csharptrainingint::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        // Ensure VideoView is prepared and starts playing when the activity resumes
        videoView.setVideoURI(videoUri)
        videoView.seekTo(currentPosition)
        videoView.start()
    }
    private fun setupVideoView() {
        videoView = findViewById(R.id.vedioviewgif)
        val videoPath = "android.resource://$packageName/${R.raw.gif}"
        videoUri = Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)

        videoView.setOnCompletionListener {
            // Restart the video when it completes playing
            videoView.start()
        }
        binding.backbtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, HomePage::class.java))
            finish()
        })
    }
}