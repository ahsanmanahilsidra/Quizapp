package com.example.techtriv_fyp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techtriv_fyp.Model.QuizListModel
import com.example.techtriv_fyp.repository.QuizListRepository

class QuizListViewModel : ViewModel(), QuizListRepository.OnFirestoreTaskComplete {


    private val quizListLiveData = MutableLiveData<List<QuizListModel>>()

    private val repository = QuizListRepository(this)

    // Public method to get the quiz list live data
    fun getQuizListLiveData(): MutableLiveData<List<QuizListModel>> {
        return quizListLiveData
    }
    init {
        repository.getQuizData()
    }

    override fun quizDataLoaded(quizListModels: List<QuizListModel>?) {
        quizListLiveData.value = quizListModels ?: emptyList()
    }

    override fun onError(e: Exception?) {
        Log.d("QuizERROR", "onError: ${e?.message}")
    }
}