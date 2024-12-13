package com.plantapphubx.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentHomeBinding
import com.plantapphubx.ui.MainActivity
import com.plantapphubx.ui.onboardings.OnboardingActivity
import com.plantapphubx.ui.paywall.PaywallActivity
import com.plantapphubx.utils.CustomAdaptiveDecoration
import com.plantapphubx.utils.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var questionsAdapter: HomeQuestionsAdapter
    private lateinit var categoriesAdapter: HomeCategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPremiumStatus()
        viewModel.fetchQuestions()
        viewModel.fetchCategories()

        binding.llPremium.clickWithDebounce {
            val intent = Intent(requireContext(), PaywallActivity::class.java)
            startActivity(intent)
            (activity as? PaywallActivity)?.finish()

        }

        // Initialize Adapters
        questionsAdapter = HomeQuestionsAdapter(emptyList()) { question ->
            // Handle question click
        }
        categoriesAdapter = HomeCategoriesAdapter(emptyList()) { category ->
            // Handle category click
        }

        // Setup RecyclerViews
        setupQuestionsRecyclerView()
        setupCategoriesRecyclerView()

        // Observe ViewModel LiveData
        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            questionsAdapter.updateData(questions)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.updateData(categories)
        }

        // Search bar filtering
        binding.homeSearchbar.addTextChangedListener { editable ->
            val query = editable.toString()
            filterCategories(query)
        }
    }

    private fun setupQuestionsRecyclerView() {
        binding.questionRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.questionRecyclerview.adapter = questionsAdapter

        val spacing = 12 // dp
        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(),
            spanCount = 1,
            spacingHorizontal = spacing,
            spacingVertical = 0,
            includeEdge = true
        )
        binding.questionRecyclerview.addItemDecoration(itemDecoration)
    }

    private fun setupCategoriesRecyclerView() {
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoriesRecyclerView.adapter = categoriesAdapter

        val spacing = 24
        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(),
            spanCount = 2,
            spacingHorizontal = spacing,
            spacingVertical = spacing,
            includeEdge = true
        )
        binding.categoriesRecyclerView.addItemDecoration(itemDecoration)
    }

    private fun filterCategories(query: String) {
        val filteredCategories = viewModel.filterCategories(query)
        categoriesAdapter.updateData(filteredCategories)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.changeFullScreenFlags(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.changeFullScreenFlags(false)
    }

    private fun checkPremiumStatus() {
        val sharedPreferences = requireContext().getSharedPreferences("PaywallPrefs", Context.MODE_PRIVATE)
        val isPremium = sharedPreferences.getBoolean("isPremium", false) // default to false if not found

        if (isPremium) {
            binding.llPremium.visibility = View.GONE
        }
    }
}
