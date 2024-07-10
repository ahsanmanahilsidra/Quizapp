package com.example.techtriv_fyp.views

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.techtriv_fyp.R
import com.example.techtriv_fyp.repository.QuestionViewModel

class QuizFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: QuestionViewModel
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar
    private lateinit var option1Btn: Button
    private lateinit var option2Btn: Button
    private lateinit var option3Btn: Button
    private lateinit var nextQueBtn: Button
    private lateinit var questionTv: TextView
    private lateinit var ansFeedBackTv: TextView
    private lateinit var questionNumberTv: TextView
    private lateinit var timerCountTv: TextView
    private lateinit var closeQuizBtn: ImageView
    private var quizId: String = ""
    private var totalQuestions: Long = 0
    private var currentQueNo: Int = 0
    private var canAnswer: Boolean = false
    private var timer: Long = 0
    private lateinit var countDownTimer: CountDownTimer
    private var notAnswered: Int = 0
    private var correctAnswer: Int = 0
    private var wrongAnswer: Int = 0
    private var answer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(QuestionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        closeQuizBtn = view.findViewById(R.id.imageView3)
        option1Btn = view.findViewById(R.id.option1Btn)
        option2Btn = view.findViewById(R.id.option2Btn)
        option3Btn = view.findViewById(R.id.option3Btn)
        nextQueBtn = view.findViewById(R.id.nextQueBtn)
        ansFeedBackTv = view.findViewById(R.id.ansFeedbackTv)
        questionTv = view.findViewById(R.id.quizQuestionTv)
        timerCountTv = view.findViewById(R.id.countTimeQuiz)
        questionNumberTv = view.findViewById(R.id.quizQuestionsCount)
        progressBar = view.findViewById(R.id.quizCoutProgressBar)

        quizId = QuizFragmentArgs.fromBundle(requireArguments()).quizId
        Log.d("QuizFragment", "Quiz ID from arguments: $quizId")
        totalQuestions = QuizFragmentArgs.fromBundle(requireArguments()).totalQueCount
        viewModel.setQuizId(quizId)
        viewModel.getQuestions()

        option1Btn.setOnClickListener(this)
        option2Btn.setOnClickListener(this)
        option3Btn.setOnClickListener(this)
        nextQueBtn.setOnClickListener(this)

        closeQuizBtn.setOnClickListener {
            navController.navigate(R.id.action_quizFragment2_to_listFragment)
        }

        viewModel.getQuestionMutableLiveData().observe(viewLifecycleOwner, Observer { questionModels ->
            if (questionModels.isNotEmpty()) {
                loadQuestions(1)
            } else {
                // Handle empty questions list
            }
        })

        loadData()
    }

    private fun loadData() {
        enableOptions()
        loadQuestions(1)
    }

    private fun enableOptions() {
        option1Btn.visibility = View.VISIBLE
        option2Btn.visibility = View.VISIBLE
        option3Btn.visibility = View.VISIBLE

        option1Btn.isEnabled = true
        option2Btn.isEnabled = true
        option3Btn.isEnabled = true

        ansFeedBackTv.visibility = View.INVISIBLE
        nextQueBtn.visibility = View.INVISIBLE
    }

    private fun loadQuestions(i: Int) {
        currentQueNo = i
        viewModel.questionMutableLiveData.observe(viewLifecycleOwner, Observer { questionModels ->
            if (questionModels.isNotEmpty()) {
                val question = questionModels[currentQueNo - 1]
                questionTv.text = "${currentQueNo}) ${question.question}"
                option1Btn.text = question.option_a
                option2Btn.text = question.option_b
                option3Btn.text = question.option_c
                timer = question.timer
                answer = question.answer.toString()

                questionNumberTv.text = currentQueNo.toString()
                startTimer()
            } else {
                Log.d("QuizFragment", "No questions found.")
            }
        })
        canAnswer = true
    }
    private fun startTimer() {
        timerCountTv.text = timer.toString()
        progressBar.visibility = View.VISIBLE

        countDownTimer = object : CountDownTimer(timer * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerCountTv.text = (millisUntilFinished / 1000).toString()
                val percent = millisUntilFinished / (timer * 10)
                progressBar.progress = percent.toInt()
            }

            override fun onFinish() {
                canAnswer = false
                ansFeedBackTv.text = "Times Up !! No answer selected"
                notAnswered++
                showNextBtn()
            }
        }.start()
    }

    private fun showNextBtn() {
        if (currentQueNo == totalQuestions.toInt()) {
            nextQueBtn.text = "Submit"
            nextQueBtn.isEnabled = true
            nextQueBtn.visibility = View.VISIBLE
        } else {
            nextQueBtn.visibility = View.VISIBLE
            nextQueBtn.isEnabled = true
            ansFeedBackTv.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.option1Btn -> verifyAnswer(option1Btn)
            R.id.option2Btn -> verifyAnswer(option2Btn)
            R.id.option3Btn -> verifyAnswer(option3Btn)
            R.id.nextQueBtn -> {
                if (currentQueNo == totalQuestions.toInt()) {
                    submitResults()
                } else {
                    currentQueNo++
                    loadQuestions(currentQueNo)
                    resetOptions()
                }
            }
        }
    }

    private fun resetOptions() {
        ansFeedBackTv.visibility = View.INVISIBLE
        nextQueBtn.visibility = View.INVISIBLE
        nextQueBtn.isEnabled = false
        option1Btn.background = ContextCompat.getDrawable(requireContext(), R.color.gray)
        option2Btn.background = ContextCompat.getDrawable(requireContext(), R.color.gray)
        option3Btn.background = ContextCompat.getDrawable(requireContext(), R.color.gray)
    }

    private fun submitResults() {
        val resultMap = HashMap<String, Any>()
        resultMap["correct"] = correctAnswer
        resultMap["wrong"] = wrongAnswer
        resultMap["notAnswered"] = notAnswered

        viewModel.addResults(resultMap)

        val action = QuizFragmentDirections.actionQuizFragment2ToResultFragment()
        action.quizId = quizId
        navController.navigate(action)
    }

    private fun verifyAnswer(button: Button) {
        if (canAnswer) {
            if (answer == button.text) {
                button.background = ContextCompat.getDrawable(requireContext(), R.color.green)
                correctAnswer++
                ansFeedBackTv.text = "Correct Answer"
            } else {
                button.background = ContextCompat.getDrawable(requireContext(), R.color.red)
                wrongAnswer++
                ansFeedBackTv.text = "Wrong Answer \nCorrect Answer: $answer"
            }
        }
        canAnswer = false
        countDownTimer.cancel()
        showNextBtn()
    }}
