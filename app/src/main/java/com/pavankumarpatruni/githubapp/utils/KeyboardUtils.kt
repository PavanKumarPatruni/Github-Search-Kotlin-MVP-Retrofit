package com.pavankumarpatruni.githubapp.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtils {

    companion object {

        fun hideKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}