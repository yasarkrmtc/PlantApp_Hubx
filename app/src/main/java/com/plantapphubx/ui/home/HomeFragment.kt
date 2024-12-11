package com.plantapphubx.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentHomeBinding
import com.plantapphubx.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeQuestionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeQuestionsAdapter(emptyList()) { question ->

        }

        binding.questionRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.questionRecyclerview.adapter = adapter

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            adapter.updateData(questions)
        }

        viewModel.fetchQuestions()
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