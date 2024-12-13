package com.plantapphubx.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.databinding.CategoriesItemBinding

class HomeCategoriesAdapter(
    private var categories: List<CategoryDataUIModel>,
    private val onItemClick: (CategoryDataUIModel) -> Unit
) : RecyclerView.Adapter<HomeCategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryDataUIModel) {
            binding.categoriesText.text = category.title
            Glide.with(binding.categoriesImageView.context)
                .load(category.image.url)
                .into(binding.categoriesImageView)

            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoriesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    fun updateData(newCategories: List<CategoryDataUIModel>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}