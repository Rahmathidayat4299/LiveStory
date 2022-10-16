package com.dicoding.livestory.customview

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.livestory.R
import com.dicoding.livestory.util.emailValid

/**
 * Created by Rahmat Hidayat on 08/10/2022.
 */
class EmailView : AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validationEmail()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }


    private fun validationEmail() {
        val email = text?.trim()
        error = if (email.isNullOrEmpty()) {
            resources.getString(R.string.email_valid)
        } else if (!emailValid(email.toString())) {
            resources.getString(R.string.invalid_email)
        } else {
            return
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) validationEmail()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }
}