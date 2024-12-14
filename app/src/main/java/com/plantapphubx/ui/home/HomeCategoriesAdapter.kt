package com.plantapphubx.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.databinding.CategoriesItemBinding
import com.plantapphubx.utils.clickWithDebounce

class HomeCategoriesAdapter(
    private var categories: List<CategoryDataUIModel>,
    private val onItemClick: (CategoryDataUIModel) -> Unit
) : RecyclerView.Adapter<HomeCategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryDataUIModel) {
            bindCategoryText(category.title)
            bindCategoryImage(category.image.url)
            setupItemClickListener(category)
        }

        private fun bindCategoryText(title: String) {
            binding.categoriesText.text = title
        }

        private fun bindCategoryImage(imageUrl: String) {
            Glide.with(binding.categoriesImageView.context)
                .load(imageUrl)
                .into(binding.categoriesImageView)
        }

        private fun setupItemClickListener(category: CategoryDataUIModel) {
            binding.root.clickWithDebounce {
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