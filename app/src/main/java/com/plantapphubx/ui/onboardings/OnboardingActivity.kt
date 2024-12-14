package com.plantapphubx.ui.onboardings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.plantapphubx.databinding.ActivityOnboardingBinding
import com.plantapphubx.ui.MainActivity
import com.plantapphubx.ui.paywall.PaywallActivity
import com.plantapphubx.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(Constants.ONBOARDING_PREFS, Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences(Constants.PAYWALL_PREFS, Context.MODE_PRIVATE)

        handleOnboarding(sharedPreferences, sharedPreferences2)
    }

    private fun handleOnboarding(sharedPreferences: SharedPreferences, sharedPreferences2: SharedPreferences) {
        val hasSeenOnboarding = sharedPreferences.getBoolean(Constants.SEEN, false)
        val isPremium = sharedPreferences2.getBoolean(Constants.IS_PREMIUM, false)

        when {
            hasSeenOnboarding && isPremium -> navigateToMainActivity()
            hasSeenOnboarding -> navigateToPaywallActivity()
            else -> startOnboardingFlow()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToPaywallActivity() {
        val intent = Intent(this, PaywallActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startOnboardingFlow() {}

    fun changeFullScreenFlags(isFullScreen: Boolean) {
        if (isFullScreen) {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}