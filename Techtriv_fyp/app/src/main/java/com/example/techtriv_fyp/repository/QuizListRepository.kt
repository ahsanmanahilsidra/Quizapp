package com.example.techtriv_fyp.repository

import com.example.techtriv_fyp.Model.QuizListModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class QuizListRepository(private val onFirestoreTaskComplete: OnFirestoreTaskComplete) {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val reference: CollectionReference = firebaseFirestore.collection("Quiz")

    fun getQuizData() {
        reference.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onFirestoreTaskComplete.quizDataLoaded(task.result?.toObjects(QuizListModel::class.java))
                } else {
                    onFirestoreTaskComplete.onError(task.exception)
                }
            }
    }

    interface OnFirestoreTaskComplete {
        fun quizDataLoaded(quizListModels: List<QuizListModel>?)
        fun onError(e: Exception?)
    }
}