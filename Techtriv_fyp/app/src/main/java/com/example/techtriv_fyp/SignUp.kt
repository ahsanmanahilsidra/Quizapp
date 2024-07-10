package com.example.techtriv_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.techtriv_fyp.Models.Users
import com.example.techtriv_fyp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUp : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var signButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btn1.setOnClickListener {


            if (binding.TextEmail.text.toString()
                    .equals("") && binding.TextPassword.text.toString().equals("")
            ) {
                Toast.makeText(this, "Please fill all the data ", Toast.LENGTH_SHORT).show()
            } else if (binding.TextPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Password Required ", Toast.LENGTH_SHORT).show()
            } else if (binding.TextEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "Email Required ", Toast.LENGTH_SHORT).show()
            } else if (binding.username.text.toString().isEmpty()) {
                Toast.makeText(this, "Username Required ", Toast.LENGTH_SHORT).show()
            } else if (binding.age.text.toString().isEmpty()) {
                Toast.makeText(this, "Age Required ", Toast.LENGTH_SHORT).show()
            } else if (containsSpecialCharacter(binding.TextPassword.text.toString()) == false) {
                Toast.makeText(
                    this,
                    "Password Should contain a Special chracter",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.TextEmail.text.toString(),
                    binding.TextPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        var user: Users = Users(
                            binding.username.text.toString(),
                            binding.age.text.toString(),
                            binding.TextEmail.text.toString(),
                            binding.TextPassword.text.toString()
                        )
                        Firebase.firestore.collection("users")
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                        startActivity(Intent(this@SignUp, Instruction::class.java))
                    } else {
                        Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.Loginnext.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


    fun containsSpecialCharacter(password: String): Boolean {
        val specialCharacters = setOf(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '-',
            '_',
            '+',
            '=',
            '{',
            '}',
            '[',
            ']',
            '|',
            '\\',
            ';',
            ':',
            '\'',
            '"',
            '<',
            '>',
            ',',
            '.',
            '/',
            '?'
        )

        for (char in password) {
            if (specialCharacters.contains(char)) {
                return true
            }
        }
        return false
    }
}