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
import com.plantapphubx.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaywallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaywallBinding
    private var selectedPlan: String? = null
    private val monthlyPlan = Constants.MONTHLY_PLAN
    private val yearlyPlan = Constants.YEARLY_PLAN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            selectPlan(yearlyPlan)

            closeButton.setOnClickListener {
                navigateToMainActivity()
            }

            monthlyCardView.setOnClickListener {
                selectPlan(monthlyPlan)
            }

            yearlyCardView.setOnClickListener {
                selectPlan(yearlyPlan)
            }

            tryButton.setOnClickListener {
                handlePlanSelection()
            }
        }
    }

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

    private fun handlePlanSelection() {
        when (selectedPlan) {
            monthlyPlan -> {
                savePremiumStatus(isPremium = true, plan = monthlyPlan)
                Toast.makeText(this@PaywallActivity, Constants.TOAST_MONTHLY_PURCHASE, Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
            yearlyPlan -> {
                savePremiumStatus(isPremium = true, plan = yearlyPlan)
                Toast.makeText(this@PaywallActivity, Constants.TOAST_YEARLY_PURCHASE, Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
            else -> {
                Toast.makeText(this@PaywallActivity, Constants.TOAST_PLEASE_SELECT_PLAN, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savePremiumStatus(isPremium: Boolean, plan: String) {
        val sharedPreferences = getSharedPreferences(Constants.PAYWALL_PREFS, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean(Constants.IS_PREMIUM, isPremium)
            putString(Constants.SELECTED_PLAN, plan)
            commit()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@PaywallActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun changeFullScreenFlags(isFullScreen: Boolean) {
        if (isFullScreen) {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
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