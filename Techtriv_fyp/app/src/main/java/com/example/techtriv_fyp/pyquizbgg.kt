package com.example.techtriv_fyp

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.techtriv_fyp.databinding.ActivityPyquizbgBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class pyquizbgg : AppCompatActivity() {
    private lateinit var questionsList: List<Map<String, Any>>
    private var currentIndex = 0
    private var score = 0
    private lateinit var answer: String
    private lateinit var binding: ActivityPyquizbgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPyquizbgBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var id = intent.getStringExtra("document")
        var collection = intent.getStringExtra("collection")
        var name = intent.getStringExtra("name")
        val db = FirebaseFirestore.getInstance()
        val quizRef = db.collection(collection.toString()).document(id.toString())
        val questionRef = quizRef.collection("questions")
        loadQuestions(questionRef)

        setupButtonClickListeners()

        binding.nextQueBtn.setOnClickListener {
            val selectedOption = getSelectedOption()
            if (selectedOption.isEmpty()) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            } else {
                if (selectedOption == answer) {
                    score++
                    binding.textView.text = score.toString()
                    // Example feedback message for correct answer
                } else {
                    // Example feedback message for incorrect answer

                }
                if (currentIndex < questionsList.size - 1) {
                    currentIndex++
                    displayQuestion(currentIndex)
                } else {
                    // Example: Quiz completed, show a dialog or navigate to result screen
                    showQuizCompletionDialog(name.toString())
                }
                resetButtonColors()
            }
        }
    }

    private fun loadQuestions(questionRef: CollectionReference) {
        questionRef.get()
            .addOnSuccessListener { querySnapshot ->
                questionsList = querySnapshot.documents.map { doc ->
                    doc.data ?: emptyMap()
                }
                if (questionsList.isNotEmpty()) {
                    displayQuestion(currentIndex)
                } else {
                    Log.e(TAG, "No questions found")
                    Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching questions", exception)
                Toast.makeText(this, "Error fetching questions", Toast.LENGTH_SHORT).show()
            }
    }

    private fun displayQuestion(index: Int) {
        val questionData = questionsList[index]
        binding.quizQuestionTv.text = questionData["question"] as? String ?: ""
        binding.option1Btn.text = questionData["option_a"] as? String ?: ""
        binding.option2Btn.text = questionData["option_b"] as? String ?: ""
        binding.option3Btn.text = questionData["option_c"] as? String ?: ""
        answer = questionData["answer"] as? String ?: ""
        // Reset feedback text view
        binding.ansFeedbackTv.setOnClickListener(View.OnClickListener {
            binding.ansFeedbackTv.setText(answer.toString())
        })
    }

    private fun setupButtonClickListeners() {
        val blackColor = ContextCompat.getColor(this, R.color.red)
        val orangeColor = ContextCompat.getColor(this, R.color.blue)

        binding.option1Btn.setOnClickListener {
            toggleButtonColor(binding.option1Btn, blackColor, orangeColor)
        }
        binding.option2Btn.setOnClickListener {
            toggleButtonColor(binding.option2Btn, blackColor, orangeColor)
        }
        binding.option3Btn.setOnClickListener {
            toggleButtonColor(binding.option3Btn, blackColor, orangeColor)
        }
    }

    private fun toggleButtonColor(button: View, blackColor: Int, orangeColor: Int) {
        val currentState = button.backgroundTintList
        if (currentState == null || currentState.defaultColor == orangeColor) {
            button.backgroundTintList = ColorStateList.valueOf(blackColor)
        } else {
            button.backgroundTintList = ColorStateList.valueOf(orangeColor)
        }
    }

    private fun getSelectedOption(): String {
        var selectedOption = ""
        if (binding.option1Btn.backgroundTintList?.defaultColor == ContextCompat.getColor(this, R.color.red)) {
            selectedOption = "a"
        } else if (binding.option2Btn.backgroundTintList?.defaultColor == ContextCompat.getColor(this, R.color.red)) {
            selectedOption = "b"
        } else if (binding.option3Btn.backgroundTintList?.defaultColor == ContextCompat.getColor(this, R.color.red)) {
            selectedOption = "c"
        }
        return selectedOption
    }

    private fun resetButtonColors() {
        binding.option1Btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
        binding.option2Btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
        binding.option3Btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
        binding.ansFeedbackTv.setText("verify answer")
    }

    private fun showQuizCompletionDialog(name:String) {
        val intent = Intent(this, showresult::class.java)
        // Add the score as extra data
        intent.putExtra("SCORE", score)
        intent.putExtra("name",name.toString())
        // Start the activity
        startActivity(intent)
        // Finish current activity (optional: depends on whether you want to keep the quiz activity in the back stack)
        finish()
    }

    companion object {
        private const val TAG = "pyquizbgg"
    }
}
