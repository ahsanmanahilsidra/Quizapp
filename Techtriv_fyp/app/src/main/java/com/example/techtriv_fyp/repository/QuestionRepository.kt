package com.example.techtriv_fyp.repository

import android.util.Log
import com.example.techtriv_fyp.Model.QuestionModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuestionRepository(
    private val onQuestionLoad: OnQuestionLoad,
    private val onResultAdded: OnResultAdded,
    private val onResultLoad: OnResultLoad
) {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var quizId: String? = null
    private val resultMap: HashMap<String, Long> = HashMap()
    private val currentUserId: String? = FirebaseAuth.getInstance().currentUser?.uid

    fun getResults() {
        quizId?.let { id ->
            currentUserId?.let { userId ->
                firebaseFirestore.collection("Quiz").document(id)
                    .collection("results").document(userId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val data = documentSnapshot.data
                        if (data != null) {
                            val correct = data["correct"] as? Long ?: 0
                            val wrong = data["wrong"] as? Long ?: 0
                            val notAnswered = data["notAnswered"] as? Long ?: 0
                            resultMap["correct"] = correct
                            resultMap["wrong"] = wrong
                            resultMap["notAnswered"] = notAnswered
                            onResultLoad.onResultLoad(resultMap)
                        } else {
                            onResultLoad.onError(Exception("Result document not found"))
                        }
                    }
                    .addOnFailureListener { e ->
                        onResultLoad.onError(e)
                    }
            } ?: run {
                onResultLoad.onError(Exception("Current user ID is null"))
            }
        } ?: run {
            onResultLoad.onError(Exception("Quiz ID is null"))
        }
    }

    fun addResults(resultMap: HashMap<String, Any>) {
        quizId?.let { id ->
            currentUserId?.let { userId ->
                firebaseFirestore.collection("Quiz").document(id)
                    .collection("results").document(userId)
                    .set(resultMap)
                    .addOnSuccessListener {
                        onResultAdded.onSubmit()
                    }
                    .addOnFailureListener { e ->
                        onResultAdded.onError(e)
                    }
            } ?: run {
                onResultAdded.onError(Exception("Current user ID is null"))
            }
        } ?: run {
            onResultAdded.onError(Exception("Quiz ID is null"))
        }
    }

    fun setQuizId(quizId: String) {
        this.quizId = quizId
        Log.d("QuizFragment", "Quiz ID set to: $quizId")
    }
    fun getQuestions() {
        quizId?.let { id ->
            Log.d("QuestionRepository", "Fetching questions for Quiz ID: $id")
            firebaseFirestore.collection("Quiz").document(id).collection("questions")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val questionModels = querySnapshot.toObjects(QuestionModel::class.java)
                    Log.d("QuestionRepository", "Questions loaded: ${questionModels.size}")
                    onQuestionLoad.onLoad(questionModels)
                }
                .addOnFailureListener { e ->
                    Log.e("QuestionRepository", "Error loading questions", e)
                    onQuestionLoad.onError(e)
                }
        } ?: run {
            onQuestionLoad.onError(Exception("Quiz ID is null"))
        }
    }

    interface OnQuestionLoad {
        fun onLoad(questionModels: List<QuestionModel>)
        fun onError(e: Exception)
    }

    interface OnResultAdded {
        fun onSubmit(): Boolean
        fun onError(e: Exception)
    }

    interface OnResultLoad {
        fun onResultLoad(resultMap: HashMap<String, Long>)
        fun onError(e: Exception)
    }
}
