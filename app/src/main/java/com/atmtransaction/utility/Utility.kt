package com.atmtransaction.utility

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object Utility {
    fun hideKeyboard(ctx: Context) {
        val inputManager =
            ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}