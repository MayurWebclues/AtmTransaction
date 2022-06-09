package com.atmtransaction.utility

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.phoneWatcher(phoneValidation: (isValid: Boolean) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(value: Editable?) {
            value?.trim()?.let { phoneValidation(it.length < Constants.MIN_LENGTH) }
        }
    })
}