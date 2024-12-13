package com.plantapphubx.ui.paywall

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.plantapphubx.R
import com.plantapphubx.databinding.ActivityPaywallBinding
import com.plantapphubx.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaywallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaywallBinding
    private var selectedPlan: String? = null
    private val monthlyPlan = "MONTHLY"
    private val yearlyPlan = "YEARLY"


    private fun resetSelection() {
        binding.apply {
            monthlyCardView.strokeColor = ContextCompat.getColor(this@PaywallActivity, R.color.white)
            unselectRadio.setImageResource(R.drawable.unselected_radio_btn)
            yearlyCardView.strokeColor = ContextCompat.getColor(this@PaywallActivity, R.color.white)
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
                        ContextCompat.getColor(this@PaywallActivity, R.color.selected_border)
                    unselectRadio.setImageResource(R.drawable.selected_radio_btn)
                }
                yearlyPlan -> {
                    yearlyCardView.strokeColor =
                        ContextCompat.getColor(this@PaywallActivity, R.color.selected_border)
                    selectRadio.setImageResource(R.drawable.selected_radio_btn)
                }
            }
            selectedPlan = plan
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI(){
        binding.apply {
            selectPlan(yearlyPlan)

            closeButton.setOnClickListener {
                val intent = Intent(this@PaywallActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
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
                        Toast.makeText(this@PaywallActivity, "Aylık premium paketi aldınız", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@PaywallActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    yearlyPlan -> {
                        savePremiumStatus(isPremium = true, plan = yearlyPlan)
                        Toast.makeText(this@PaywallActivity, "Yıllık abonelik aldınız. İlk 3 gün ücretsiz!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@PaywallActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        Toast.makeText(this@PaywallActivity, "Lütfen bir plan seçin!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun changeFullScreenFlags(isFullScreen: Boolean) {
        if (isFullScreen) {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
    private fun savePremiumStatus(isPremium: Boolean, plan: String) {
        val sharedPreferences = getSharedPreferences("PaywallPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean("isPremium", isPremium)
            putString("selectedPlan", plan)
            apply()
        }
    }

    private fun isPremiumUser(): Boolean {
        val sharedPreferences = getSharedPreferences("PaywallPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isPremium", false)
    }

    override fun onResume() {
        super.onResume()
        changeFullScreenFlags(true)
    }

    override fun onPause() {
        super.onPause()
        changeFullScreenFlags(false)
    }
}