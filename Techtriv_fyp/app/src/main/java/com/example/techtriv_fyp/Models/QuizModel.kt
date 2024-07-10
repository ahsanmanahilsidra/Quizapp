package com.example.techtriv_fyp.Models

data class QuizModel(
    val id : String,
    val title : String,
    val subtitle : String,
    val time : String,
    val questionList : List<QuestionModel>
){
    constructor() : this("","","","", emptyList())
}

/*data class QuestionModel(
    val question : String,
    val options : List<String>,
    val correct : String,
){
    constructor() : this ("", emptyList(),"")
}*/
data class QuestionModel(
    val question : String,
    val options : MutableList<String>, // Change to MutableList
    val correct : String,
){
    constructor() : this ("", mutableListOf(),"")
}