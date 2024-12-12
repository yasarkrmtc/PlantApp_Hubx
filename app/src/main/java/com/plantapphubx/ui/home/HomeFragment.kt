package com.plantapphubx.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
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

        viewModel.fetchQuestions()
        viewModel.fetchCategories()

        questionsAdapter = HomeQuestionsAdapter(emptyList()) { question ->
        }
        binding.questionRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.questionRecyclerview.adapter = questionsAdapter

        categoriesAdapter = HomeCategoriesAdapter(emptyList()) { category ->
        }
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoriesRecyclerView.adapter = categoriesAdapter

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            questionsAdapter.updateData(questions)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.updateData(categories)
        }

        binding.homeSearchbar.addTextChangedListener { editable ->
            val query = editable.toString()
            filterCategories(query)
        }
    }

    private fun filterCategories(query: String) {
        val filteredCategories = viewModel.filterCategories(query)
        categoriesAdapter.updateData(filteredCategories)
    }
}
