package com.example.techtriv_fyp.Model

import com.google.firebase.firestore.DocumentId

data class QuizListModel(
    @DocumentId
    var quizId: String? = null,
    var title: String? = null,
    var image: String? = null,
    var difficulty: String? = null,
    var questions: Long = 0
)