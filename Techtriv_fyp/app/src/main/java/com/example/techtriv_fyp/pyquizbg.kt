package com.example.techtriv_fyp

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.techtriv_fyp.Model.QuestionModel
import com.example.techtriv_fyp.databinding.ActivityPyquizbgBinding
import com.example.techtriv_fyp.databinding.ScoreDialogBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class Pyquizbg : AppCompatActivity() {

    private lateinit var binding: ActivityPyquizbgBinding // Initialize the binding variable

    private var currentIndex = 0
    private var score = 0
    private var answer = ""
    private lateinit var questionsList: List<QuestionModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPyquizbgBinding.inflate(layoutInflater) // Inflate the layout using view binding
        setContentView(binding.root)

        val id: String? = intent.getStringExtra("id")
        if (id.isNullOrEmpty()) {
            Log.e(TAG, "Quiz ID is null or empty")
            finish()
            return
        }

        val db = FirebaseFirestore.getInstance()
        val quizRef = db.collection("Quiz").document(id!!)
        val questionRef = quizRef.collection("questions")

        loadQuestions(questionRef)

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

        binding.nextQueBtn.setOnClickListener {
            val selectedOption = getSelectedOption()
            if (selectedOption.isEmpty()) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            } else {
                if (selectedOption == answer) {
                    score++
                    binding.textView.text = score.toString()
                }
                if (currentIndex < questionsList.size) {
                    currentIndex++
                    displayQuestion(currentIndex)
                } else {
                    // Quiz finished, show result or handle end of quiz
                    showResult()
                }
                resetButtonColors()
            }
        }
    }

    private fun loadQuestions(questionRef: CollectionReference) {
        questionRef.get()
            .addOnSuccessListener { querySnapshot ->
                questionsList = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(QuestionModel::class.java)
                }
                if (questionsList.isNotEmpty()) {
                    displayQuestion(currentIndex)
                } else {
                    Log.e(TAG, "No questions found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching questions", exception)
            }
    }

    private fun displayQuestion(index: Int) {
        val questionData = questionsList[index]
        binding.quizQuestionTv.text = questionData.question
        binding.option1Btn.text = questionData.option_a
        binding.option2Btn.text = questionData.option_b
        binding.option3Btn.text = questionData.option_c
        answer = questionData.answer
        // Reset feedback text view
        binding.ansFeedbackTv.text = ""
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
            selectedOption += "a"
        }
        if (binding.option2Btn.backgroundTintList?.defaultColor == ContextCompat.getColor(this, R.color.red)) {
            selectedOption += "b"
        }
        if (binding.option3Btn.backgroundTintList?.defaultColor == ContextCompat.getColor(this, R.color.red)) {
            selectedOption += "c"
        }
        return selectedOption
    }

    private fun resetButtonColors() {
        binding.option1Btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
        binding.option2Btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
        binding.option3Btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
    }

    private fun showResult() {
        // Implement logic to show result or end of quiz actions
        // For example, you can start a new activity to show the score or display a dialog
        val intent = Intent(this, ScoreDialogBinding::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
    }

    companion object {
        const val TAG = "PyquizbgActivity"
    }
}
