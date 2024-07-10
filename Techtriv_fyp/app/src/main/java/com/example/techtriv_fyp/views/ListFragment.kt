package com.example.techtriv_fyp.views
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techtriv_fyp.Adapter.QuizListAdapter
import com.example.techtriv_fyp.R
import com.example.techtriv_fyp.viewmodel.QuizListViewModel


class ListFragment : Fragment(), QuizListAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var navController: NavController
    private lateinit var viewModel: QuizListViewModel
    private lateinit var adapter: QuizListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(QuizListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.listQuizRecyclerview)
        progressBar = view.findViewById(R.id.quizListProgressbar)
        navController = Navigation.findNavController(view)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = QuizListAdapter(this)

        recyclerView.adapter = adapter

        viewModel.getQuizListLiveData().observe(viewLifecycleOwner, Observer { quizListModels ->
            progressBar.visibility = View.GONE
            adapter.setQuizListModels(quizListModels)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(position)
        navController.navigate(action)
    }
}