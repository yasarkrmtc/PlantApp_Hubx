package com.plantapphubx.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plantapphubx.R
import com.plantapphubx.data.local.QuestionsUIModel
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
                loadImage(imageView.context, question.imageUri)

                root.clickWithDebounce {
                    onItemClick(question)
                    question.uri?.let { openQuestionUri(it) }
                }
            }
        }

        private fun loadImage(context: Context, imageUri: String?) {
            Glide.with(context)
                .load(imageUri ?: R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageView)
        }

        private fun openQuestionUri(uri: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            binding.root.context.startActivity(intent)
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