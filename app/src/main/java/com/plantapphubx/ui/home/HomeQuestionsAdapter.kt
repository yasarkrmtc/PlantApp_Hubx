package com.plantapphubx.ui.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plantapphubx.R
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.data.remote.Questions
import com.plantapphubx.databinding.QuestionsItemBinding
import com.plantapphubx.utils.clickWithDebounce

class HomeQuestionsAdapter(
    private var questions: List<QuestionsUIModel>,
    private val onItemClick: (QuestionsUIModel) -> Unit
) : RecyclerView.Adapter<HomeQuestionsAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(private val binding: QuestionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: QuestionsUIModel) {
            binding.apply {
                questionText.text = question.title

                Glide.with(imageView.context)
                    .load(question.imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(com.google.android.material.R.drawable.mtrl_ic_error)
                    .into(imageView)

                root.clickWithDebounce {
                    val context = root.context
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(question.uri))
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount() = questions.size

    fun updateData(newQuestions: List<QuestionsUIModel>) {
        questions = newQuestions
        notifyDataSetChanged()
    }
}