package com.plantapphubx.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plantapphubx.data.remote.CategoryData
import com.plantapphubx.databinding.CategoriesItemBinding

class HomeCategoriesAdapter(
    private var categories: List<CategoryData>,
    private val onItemClick: (CategoryData) -> Unit
) : RecyclerView.Adapter<HomeCategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryData) {
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
        Log.d("CategoriesAdapter", "Binding Category: ${categories[position]}")
        holder.bind(categories[position])
    }


    override fun getItemCount() = categories.size

    fun updateData(newCategories: List<CategoryData>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}