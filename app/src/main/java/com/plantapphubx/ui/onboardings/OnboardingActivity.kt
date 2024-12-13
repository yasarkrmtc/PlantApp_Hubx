package com.plantapphubx.ui.onboardings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.plantapphubx.databinding.ActivityOnboardingBinding
import com.plantapphubx.ui.MainActivity
import com.plantapphubx.ui.paywall.PaywallActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("Onboarding", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("PaywallPrefs", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("seen",false) && sharedPreferences2.getBoolean("isPremium",false)){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }else if (sharedPreferences.getBoolean("seen",false)){
            val intent = Intent(this,PaywallActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun changeFullScreenFlags(isFullScreen: Boolean) {
        if (isFullScreen) {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}