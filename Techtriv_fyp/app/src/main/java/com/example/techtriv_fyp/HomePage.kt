package com.example.techtriv_fyp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.techtriv_fyp.databinding.ActivityHomePageBinding
import com.example.techtriv_fyp.databinding.ActivitySignUpBinding
import com.google.firebase.firestore.core.View
class HomePage : AppCompatActivity() {
    val binding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)}
    private lateinit var csharptxt : CardView
    private lateinit var cplustxt : CardView
    private lateinit var ctxt : CardView
    private lateinit var htmltxt : CardView
    private lateinit var javatxt : CardView
    private lateinit var pythontxt : CardView
    private lateinit var backbtn : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        csharptxt = findViewById(R.id.csharpstart)
        csharptxt.setOnClickListener {

            val intent = Intent(this, csharp::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Entered in C# Language", Toast.LENGTH_SHORT).show()
        }
        cplustxt = findViewById(R.id.cplusstart)
        cplustxt.setOnClickListener {

            val intent = Intent(this, cplus::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Entered in C++ Language", Toast.LENGTH_SHORT).show()
    }

        ctxt = findViewById(R.id.cstart)
        ctxt.setOnClickListener {
            val intent = Intent(this, c::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Entered in C Language", Toast.LENGTH_SHORT).show()
        }
        javatxt = findViewById(R.id.javastart)
        javatxt.setOnClickListener {

            val intent = Intent(this, java::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Entered in Java Language", Toast.LENGTH_SHORT).show()
        }
        htmltxt = findViewById(R.id.htmlstart)
        htmltxt.setOnClickListener {

            val intent = Intent(this, html::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Entered in Html Language", Toast.LENGTH_SHORT).show()
        }
        pythontxt = findViewById(R.id.pythonstart)
        pythontxt.setOnClickListener {

            val intent = Intent(this, python::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Entered in Python Language", Toast.LENGTH_SHORT).show()
        }
      binding.profile.setOnClickListener(android.view.View.OnClickListener {
          startActivity(Intent(this,userprofile::class.java))

      })
       backbtn = findViewById(R.id.backbtn)
        backbtn.setOnClickListener {

            val intent = Intent(this, Instruction::class.java)
            startActivity(intent)
        }
    }}


