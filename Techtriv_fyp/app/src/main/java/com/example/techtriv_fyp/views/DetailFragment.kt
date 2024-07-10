package com.example.techtriv_fyp.views

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.techtriv_fyp.R
import com.example.techtriv_fyp.repository.QuestionViewModel
import com.example.techtriv_fyp.viewmodel.QuizListViewModel

private var NavDirections.totalQueCount: Long
    get() = throw UnsupportedOperationException("Read-only property")
    set(value) {
        // No-op, as we can't set a value to a NavDirections
    }
var NavDirections.quizId: String
    get() = throw UnsupportedOperationException("Read-only property")
    set(value) {
        // No-op, as we can't set a value to a NavDirections
    }

class DetailFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var difficulty: TextView
    private lateinit var totalQuestions: TextView
    private lateinit var startQuizBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var topicImage: ImageView
    private lateinit var navController: NavController
    private lateinit var viewModel: QuizListViewModel
    private var position: Int = 0
    private lateinit var quizId: String
    private var totalQueCount: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                requireActivity().application
            )
        ).get(QuizListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.detailFragmentTitle)
        difficulty = view.findViewById(R.id.detailFragmentDifficulty)
        totalQuestions = view.findViewById(R.id.detailFragmentQuestions)
        startQuizBtn = view.findViewById(R.id.startQuizBtn)
        progressBar = view.findViewById(R.id.detailProgressBar)
        topicImage = view.findViewById(R.id.detailFragmentImage)
        navController = Navigation.findNavController(view)

        position = DetailFragmentArgs.fromBundle(requireArguments()).position

        viewModel.getQuizListLiveData().observe(viewLifecycleOwner, Observer { quizListModels ->
            val quiz = quizListModels[position]
            difficulty.text = quiz.difficulty
            title.text = quiz.title
            totalQuestions.text = quiz.questions.toString()
            Glide.with(view).load(quiz.image).into(topicImage)
            val handler = Handler()
            handler.postDelayed({
                progressBar.visibility = View.GONE
            }, 2000)
            totalQueCount = quiz.questions
            quizId = quiz.quizId.toString()
        })

        startQuizBtn.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToQuizFragment2()
            action.quizId = quizId ?: "" // Provide a default value if quizId is null
            action.totalQueCount = totalQueCount
            navController.navigate(action)
        }
    }
}