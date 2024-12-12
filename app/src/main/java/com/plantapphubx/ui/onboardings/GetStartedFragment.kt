package com.plantapphubx.ui.onboardings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.plantapphubx.R
import com.plantapphubx.base.BaseFragment
import com.plantapphubx.databinding.FragmentGetStartedBinding
import com.plantapphubx.ui.MainActivity
import com.plantapphubx.utils.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedFragment :
    BaseFragment<FragmentGetStartedBinding>(FragmentGetStartedBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            getStartedButton.clickWithDebounce{
                findNavController().navigate(R.id.action_getStartedFragment_to_firstOnboardingFragment)
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