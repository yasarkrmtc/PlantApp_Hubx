package com.plantapphubx.ui.paywall

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.plantapphubx.R
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentPaywallBinding
import com.plantapphubx.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaywallFragment :
    BaseFragment<FragmentPaywallBinding>(FragmentPaywallBinding::inflate) {

    private var selectedPlan: String? = null
    private val monthlyPlan = "MONTHLY"
    private val yearlyPlan = "YEARLY"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            selectPlan(yearlyPlan)

            closeButton.setOnClickListener {
                findNavController().navigate(R.id.action_paywallFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Premium olmadınız", Toast.LENGTH_SHORT).show()
            }

            monthlyCardView.setOnClickListener {
                selectPlan(monthlyPlan)
            }

            yearlyCardView.setOnClickListener {
                selectPlan(yearlyPlan)
            }

            tryButton.setOnClickListener {
                when (selectedPlan) {
                    monthlyPlan -> {
                        savePremiumStatus(isPremium = true, plan = monthlyPlan)
                        Toast.makeText(requireContext(), "Aylık premium paketi aldınız", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_paywallFragment_to_homeFragment)
                    }
                    yearlyPlan -> {
                        savePremiumStatus(isPremium = true, plan = yearlyPlan)
                        Toast.makeText(requireContext(), "Yıllık abonelik aldınız. İlk 3 gün ücretsiz!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_paywallFragment_to_homeFragment)
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Lütfen bir plan seçin!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun resetSelection() {
        binding.apply {
            monthlyCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.white)
            unselectRadio.setImageResource(R.drawable.unselected_radio_btn)
            yearlyCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.white)
            selectRadio.setImageResource(R.drawable.unselected_radio_btn)
            selectedPlan = null
        }
    }

    private fun selectPlan(plan: String) {
        binding.apply {
            resetSelection()
            when (plan) {
                monthlyPlan -> {
                    monthlyCardView.strokeColor =
                        ContextCompat.getColor(requireContext(), R.color.selected_border)
                    unselectRadio.setImageResource(R.drawable.selected_radio_btn)
                }
                yearlyPlan -> {
                    yearlyCardView.strokeColor =
                        ContextCompat.getColor(requireContext(), R.color.selected_border)
                    selectRadio.setImageResource(R.drawable.selected_radio_btn)
                }
            }
            selectedPlan = plan
        }
    }

    private fun savePremiumStatus(isPremium: Boolean, plan: String) {
        val sharedPreferences = requireContext().getSharedPreferences("PaywallPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean("isPremium", isPremium)
            putString("selectedPlan", plan)
            apply()
        }
    }

    private fun isPremiumUser(): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences("PaywallPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isPremium", false)
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