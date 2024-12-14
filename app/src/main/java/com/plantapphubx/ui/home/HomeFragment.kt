package com.plantapphubx.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.plantapphubx.R
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.databinding.FragmentHomeBinding
import com.plantapphubx.ui.MainActivity
import com.plantapphubx.ui.paywall.PaywallActivity
import com.plantapphubx.utils.Constants
import com.plantapphubx.utils.CustomAdaptiveDecoration
import com.plantapphubx.utils.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var questionsAdapter: HomeQuestionsAdapter
    private lateinit var categoriesAdapter: HomeCategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyTextGradient()
        setupUI()
        observeViewModel()

        questionsAdapter = HomeQuestionsAdapter(emptyList()) { question -> handleQuestionClick(question) }
        categoriesAdapter = HomeCategoriesAdapter(emptyList()) { category -> handleCategoryClick(category) }

        setupRecyclerViews()

        viewModel.fetchQuestions()
        viewModel.fetchCategories()
    }

    private fun setupUI() {
        checkPremiumStatus()

        binding.llPremium.clickWithDebounce { navigateToPaywall() }

        binding.homeSearchbar.addTextChangedListener { editable ->
            filterCategories(editable.toString())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.questions.collect { questions ->
                    questionsAdapter.updateData(questions)
                }
            }
            launch {
                viewModel.categories.collect { categories ->
                    categoriesAdapter.updateData(categories)
                }
            }
            launch {
                viewModel.loadingState.collect { isLoading ->
                    toggleProgressBar(isLoading)
                }
            }
            launch {
                viewModel.errorState.collect { errorMessage ->
                    showError(errorMessage)
                }
            }
        }
    }

    private fun toggleProgressBar(isLoading: Boolean) {
        binding.itemProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(errorMessage: String?) {
        if (!errorMessage.isNullOrEmpty()) {
            Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            viewModel.clearErrorState()
        }
    }


    private fun setupRecyclerViews() {
        setupRecyclerView(
            binding.questionRecyclerview,
            questionsAdapter,
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
            CustomAdaptiveDecoration(requireContext(), spanCount = 1, spacingHorizontal = 12, spacingVertical = 0)
        )
        setupRecyclerView(
            binding.categoriesRecyclerView,
            categoriesAdapter,
            GridLayoutManager(requireContext(), 2),
            CustomAdaptiveDecoration(requireContext(), spanCount = 2, spacingHorizontal = 24, spacingVertical = 24)
        )
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager,
        itemDecoration: RecyclerView.ItemDecoration
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun filterCategories(query: String) {
        val filteredCategories = viewModel.filterCategories(query)
        categoriesAdapter.updateData(filteredCategories)
    }

    private fun checkPremiumStatus() {
        val isPremium = requireContext().getSharedPreferences(Constants.PAYWALL_PREFS, Context.MODE_PRIVATE)
            .getBoolean(Constants.IS_PREMIUM, false)

        if (isPremium) {
            binding.llPremium.visibility = View.GONE
        }
    }

    private fun navigateToPaywall() {
        val intent = Intent(requireContext(), PaywallActivity::class.java)
        startActivity(intent)
        (activity as? PaywallActivity)?.finish()
    }

    private fun handleQuestionClick(question: QuestionsUIModel) {
    }

    private fun handleCategoryClick(category: CategoryDataUIModel) {
    }
    
    private fun applyTextGradient() {
        val premiumTitle = binding.premiumTitle
        val premiumSubtitle = binding.premiumSubtitle

        premiumTitle.let {
            val shader = LinearGradient(
                0f, 0f, 0f, it.textSize,
                intArrayOf(
                    Color.parseColor("#FFD700"),
                    Color.parseColor("#E4B046")
                ),
                null,
                Shader.TileMode.CLAMP
            )
            it.paint.shader = shader
        }

        premiumSubtitle.let {
            val shader = LinearGradient(
                0f, 0f, 0f, it.textSize,
                intArrayOf(
                    Color.parseColor("#FFDE9C"),
                    Color.parseColor("#F5C25B")
                ),
                null,
                Shader.TileMode.CLAMP
            )
            it.paint.shader = shader
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.changeFullScreenFlags(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.changeFullScreenFlags(false)
    }
}