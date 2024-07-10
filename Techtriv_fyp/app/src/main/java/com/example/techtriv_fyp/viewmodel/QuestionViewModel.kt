package com.example.techtriv_fyp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techtriv_fyp.Model.QuestionModel


class QuestionViewModel : ViewModel(), QuestionRepository.OnQuestionLoad, QuestionRepository.OnResultAdded, QuestionRepository.OnResultLoad {
    internal val questionMutableLiveData: MutableLiveData<List<QuestionModel>> by lazy {
        MutableLiveData<List<QuestionModel>>()
    }

    private val repository: QuestionRepository = QuestionRepository(this, this, this)
    private val resultMutableLiveData: MutableLiveData<HashMap<String, Long>> = MutableLiveData()

    fun getQuestionMutableLiveData(): MutableLiveData<List<QuestionModel>> {
        return questionMutableLiveData
    }

    fun getQuestions() {
        repository.getQuestions()
    }

    override fun onLoad(questionModels: List<QuestionModel>) {
        questionMutableLiveData.value = questionModels
    }
    fun getResultMutableLiveData(): MutableLiveData<HashMap<String, Long>> {
        return resultMutableLiveData
    }

    fun getResults() {
        repository.getResults()
    }



    fun addResults(resultMap: HashMap<String, Any>) {
        repository.addResults(resultMap)
    }

    fun setQuizId(quizId: String) {
        repository.setQuizId(quizId)
    }

    override fun onSubmit(): Boolean {
        return true
    }

    override fun onResultLoad(resultMap: HashMap<String, Long>) {
        resultMutableLiveData.value = resultMap
    }

    override fun onError(e: Exception) {
        Log.d("QuizError", "onError: ${e.message}")
    }
}