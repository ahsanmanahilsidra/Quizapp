package com.example.techtriv_fyp

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.ContextCompat
import com.example.techtriv_fyp.databinding.ActivityCBinding
import com.example.techtriv_fyp.databinding.ActivityHtmlBinding
import com.example.techtriv_fyp.databinding.ActivityPythonBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class html : AppCompatActivity(){
    private lateinit var videoView: VideoView
    private lateinit var videoUri: Uri
    private var currentPosition = 0
    val db = FirebaseFirestore.getInstance()
    val binding by lazy {
        ActivityHtmlBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html)
        setContentView(binding.root)
        val tintColor = ContextCompat.getColor(this, R.color.white)
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.name.text = it.data!!["name"].toString()

                }
            }
        setupVideoView()
        binding.trainingbegll.setOnClickListener(){
            val intent = Intent(this, htmltrainingbeg::class.java)
            startActivity(intent)
        }
        binding.traininginterll.setOnClickListener(){
            val intent = Intent(this, htmltrainingint::class.java)
            startActivity(intent)
        }
        binding.trainingadvll.setOnClickListener(){
            val intent = Intent(this, htmltrainngadv::class.java)
            startActivity(intent)
        }
        binding.quizbegll.setOnClickListener(){
            val intent = Intent(this, htmlquiz::class.java)
            startActivity(intent)
        }
        binding.quizbegll.setOnClickListener() {
            val  intent=Intent(this,pyquizbgg::class.java)
            intent.putExtra("collection", "Quiz")
            intent.putExtra("document", "3tmLQZOVtI53opn3i1Cb")
            intent.putExtra("name", "html eazy")
            startActivity(intent)
        }
        // Assuming currentUserId is the ID of the current user
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {
            db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("html eazy") && documentSnapshot.getString("html eazy") == "yes") {
                            binding.quizinterll.setCardBackgroundColor(tintColor)
                            binding.quizinterll.setOnClickListener() {
                                val  intent=Intent(this,pyquizbgg::class.java)
                                intent.putExtra("collection", "Quiz")
                                intent.putExtra("document", "KLwug6KRE5cqzETLm2LY")
                                intent.putExtra("name", "html mid")
                                startActivity(intent)
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting document", e)
                    // Handle failure, if needed
                }
        }
// Assuming currentUserId is the ID of the current user

        if (currentUserId != null) {
            db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("html mid") && documentSnapshot.getString("html mid") == "yes") {
                            binding.quizadvll.setCardBackgroundColor(tintColor)
                            binding.quizadvll.setOnClickListener() {
                                val  intent=Intent(this,pyquizbgg::class.java)
                                intent.putExtra("collection", "Quiz")
                                intent.putExtra("document", "UBHr10D10tHGULJFJwIw")
                                intent.putExtra("name", "html hard")
                                startActivity(intent)
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting document", e)
                    // Handle failure, if needed
                }
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