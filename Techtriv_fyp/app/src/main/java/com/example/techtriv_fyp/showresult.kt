package com.example.techtriv_fyp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.techtriv_fyp.databinding.ActivityShowresultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class showresult : AppCompatActivity() {
    val binding by lazy {
        ActivityShowresultBinding.inflate(layoutInflater)
    }
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val score = intent.getIntExtra("SCORE", 0)
        val name = intent.getStringExtra("name")
        val documentRef =
            db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid.toString())

// Update the document
        // Handle failure, if needed

        if (score >= 5) {
            binding.descriptio.setText("Conratulation you qualify for next round ")
            binding.happyImageView.setImageResource(R.drawable.happ)
            documentRef.update(name.toString(), "yes")
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully updated!")
                    // Handle success, if needed
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating document", e)

                }
        } else {
            binding.descriptio.setText("you not qualify for next round better luck next time  ")
            binding.happyImageView.setImageResource(R.drawable.sad)
            documentRef.update(name.toString(), "No")
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully updated!")
                    // Handle success, if needed
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating document", e)

                }
        }
        // Display the score
        binding.scoreTextView.text = "Your score is: $score"

    }
}