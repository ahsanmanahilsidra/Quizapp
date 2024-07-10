package com.example.techtriv_fyp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techtriv_fyp.Model.QuizListModel
import com.example.techtriv_fyp.R


class QuizListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<QuizListAdapter.QuizListViewHolder>() {

    private var quizListModels: List<QuizListModel>? = null

    fun setQuizListModels(quizListModels: List<QuizListModel>) {
        this.quizListModels = quizListModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_quiz, parent, false)
        return QuizListViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizListViewHolder, position: Int) {
        quizListModels?.get(position)?.let { model ->
            holder.title.text = model.title
            Glide.with(holder.itemView).load(model.image).into(holder.quizImage)
        }
    }

    override fun getItemCount(): Int {
        return quizListModels?.size ?: 0
    }

    inner class QuizListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.quizTitleList)
        val quizImage: ImageView = itemView.findViewById(R.id.quizImageList)
        private val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        init {
            constraintLayout.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}