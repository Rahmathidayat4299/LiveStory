package com.dicoding.livestory.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

/**
 * Created by Rahmat Hidayat on 08/10/2022.
 */
fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
        }
    })
}

fun emailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

const val BASE_URL = "https://story-api.dicoding.dev/v1/"

const val SPLASH_SEC = 4000L

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

