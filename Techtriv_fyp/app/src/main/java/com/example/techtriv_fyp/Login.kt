package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.techtriv_fyp.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
       binding.btn2.setOnClickListener(View.OnClickListener {
           Firebase.auth.signInWithEmailAndPassword(
               binding.TextEmail.text.toString().toString(),
               binding.TextPassword.text.toString()
           ).addOnCompleteListener {
               if (it.isSuccessful) {
                   startActivity(Intent(this, Instruction::class.java))
               } else {
                   Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
               }
           }
       })
        binding.signback.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

}
