package com.plantapphubx.utils

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController

fun View.clickWithDebounce(debounceTime: Long = Constants.debounceTime, action: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.clickWithDebounceAndNavigate(action: NavDirections, fragment: Fragment) {
    this.clickWithDebounce {
        findNavController(fragment).navigate(action)
    }
}

fun onBackPressed(fragmentActivity: FragmentActivity) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }
    fragmentActivity.onBackPressedDispatcher.addCallback(fragmentActivity, callback)
}

fun toastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}