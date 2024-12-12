package com.plantapphubx.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var questionsAdapter: HomeQuestionsAdapter
    private lateinit var categoriesAdapter: HomeCategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionsAdapter = HomeQuestionsAdapter(emptyList()) { question ->
            Log.d("HomeFragment", "Question Clicked: $question")
        }
        binding.questionRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.questionRecyclerview.adapter = questionsAdapter

        categoriesAdapter = HomeCategoriesAdapter(emptyList()) { category ->
            Log.d("HomeFragment", "Category Clicked: $category")
        }
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoriesRecyclerView.adapter = categoriesAdapter

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            Log.d("HomeFragment", "Questions Observed: $questions")
            questionsAdapter.updateData(questions)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            Log.d("HomeFragment", "Categories Observed: $categories")
            categoriesAdapter.updateData(categories)
        }

        viewModel.fetchQuestions()
        viewModel.fetchCategories()
    }
}
