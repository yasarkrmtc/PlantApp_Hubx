package com.plantapphubx.ui.onboardings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentSecondOnboardingBinding
import com.plantapphubx.ui.paywall.PaywallActivity
import com.plantapphubx.utils.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondOnboardingFragment :
    BaseFragment<FragmentSecondOnboardingBinding>(FragmentSecondOnboardingBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            continueButton.clickWithDebounce {
                val sharedPreferences = requireContext().getSharedPreferences("s", Context.MODE_PRIVATE)
                sharedPreferences.edit().apply {
                    putBoolean("seen", true)
                    apply()
                }
                val intent = Intent(requireContext(),PaywallActivity::class.java)
                startActivity(intent)
                (activity as? OnboardingActivity)?.finish()
            }

        }
    }
    override fun onResume() {
        super.onResume()
        (activity as? OnboardingActivity)?.changeFullScreenFlags(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as? OnboardingActivity)?.changeFullScreenFlags(false)
    }
}