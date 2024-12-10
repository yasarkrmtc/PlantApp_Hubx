package com.plantapphubx.ui.onboardings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.plantapphubx.R
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentSecondOnboardingBinding
import com.plantapphubx.ui.MainActivity
import com.plantapphubx.utils.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondOnboardingFragment :
    BaseFragment<FragmentSecondOnboardingBinding>(FragmentSecondOnboardingBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            continueButton.clickWithDebounce {
                findNavController().navigate(R.id.action_secondOnboardingFragment_to_paywallFragment)
            }

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