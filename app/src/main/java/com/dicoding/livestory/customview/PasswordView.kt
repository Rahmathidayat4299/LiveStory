package com.dicoding.livestory.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.livestory.R

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class PasswordView : AppCompatEditText {

    private lateinit var warningTrue: Drawable

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = resources.getString(R.string.password_required)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {

        warningTrue = ContextCompat.getDrawable(context, R.drawable.ic_warning) as Drawable
        transformationMethod = PasswordTransformationMethod.getInstance()
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                showWarning()
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                validatePass()
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    private fun validatePass() {
        val password = text?.trim()
        error = when {
            password.isNullOrEmpty() -> {
                resources.getString(R.string.password_valid)
            }
            password.length < 6 -> {
                resources.getString(R.string.password_invalid)
            }
            else -> {
                return
            }
        }
    }

    private fun showWarning() {
        setButtonDrawables(endOfTheText = warningTrue)
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun hideWarning() {
        setButtonDrawables()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) validatePass()
    }

}