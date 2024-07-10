package com.example.techtriv_fyp.Model

import com.google.firebase.firestore.DocumentId

data class QuestionModel(
    @DocumentId
    val question: String = "",
    val option_a: String = "",
    val option_b: String = "",
    val option_c: String = "",
    val answer: String = "",
    var timer: Long = 0
)
