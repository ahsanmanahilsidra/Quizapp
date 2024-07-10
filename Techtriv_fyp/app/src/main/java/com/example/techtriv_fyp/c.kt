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
import androidx.core.content.ContextCompat.startActivity
import com.example.techtriv_fyp.databinding.ActivityCBinding
import com.example.techtriv_fyp.databinding.ActivityJavaBinding
import com.example.techtriv_fyp.databinding.ActivityPythonBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class c : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var videoUri: Uri
    private var currentPosition = 0
    val db = FirebaseFirestore.getInstance()
    val binding by lazy {
        ActivityCBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val tintColor = ContextCompat.getColor(this, R.color.white)
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.name.text = it.data!!["name"].toString()

                }
            }


            binding.trainingbegll.setOnClickListener() {
                val intent = Intent(this, ctrainingbeg::class.java)
                startActivity(intent)
            }
            binding.traininginterll.setOnClickListener() {
                val intent = Intent(this, ctrainingint::class.java)
                startActivity(intent)
            }
            binding.trainingadvll.setOnClickListener() {
                val intent = Intent(this, ctrainingadv::class.java)
                startActivity(intent)
            }
            binding.quizbegll.setOnClickListener() {
                val  intent=Intent(this,pyquizbgg::class.java)
                intent.putExtra("collection", "C")
                intent.putExtra("document", "vForD6VTZzCI4NJ75PT3")
                intent.putExtra("name", "c eazy")
                startActivity(intent)
            }
        // Assuming currentUserId is the ID of the current user
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {
            db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("c eazy") && documentSnapshot.getString("c eazy") == "yes") {
                            // Show toast message
                            binding.quizinterll.setCardBackgroundColor(tintColor)
                            binding.quizinterll.setOnClickListener() {
                                val  intent=Intent(this,pyquizbgg::class.java)
                                intent.putExtra("collection", "C")
                                intent.putExtra("document", "vForD6VTZzCI4NJ75PT3")
                                intent.putExtra("name", "c mid")
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
                        if (documentSnapshot.contains("c mid") && documentSnapshot.getString("c mid") == "yes") {
                            binding.quizadvll.setCardBackgroundColor(tintColor)
                            binding.quizadvll.setOnClickListener() {
                                val  intent=Intent(this,pyquizbgg::class.java)
                                intent.putExtra("collection", "C")
                                intent.putExtra("document", "vForD6VTZzCI4NJ75PT3")
                                intent.putExtra("name", "c hard")
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





}