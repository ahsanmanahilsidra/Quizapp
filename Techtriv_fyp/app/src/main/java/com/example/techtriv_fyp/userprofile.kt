package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.techtriv_fyp.databinding.ActivityUserprofile2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class userprofile : AppCompatActivity() {
    val binding by lazy {
        ActivityUserprofile2Binding.inflate(layoutInflater)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it!=null){
                binding.name.text=it.data!!["name"].toString()
                binding.age.text=it.data!!["age"].toString()
                binding.email.text=it.data!!["email"].toString()
            }
        }
        binding.logout.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
        })
        binding.backbtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, HomePage::class.java))
            finish()
        })
    }
    }
