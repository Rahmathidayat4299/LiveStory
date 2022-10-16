package com.dicoding.livestory.customview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.livestory.R

/**
 * Created by Rahmat Hidayat on 09/10/2022.
 */
class NameView : AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        //do nothing
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //do nothing

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //do nothing
    }


    private fun validateName() {
        val name = text?.trim()
        if (name.isNullOrEmpty()) {
            error = resources.getString(R.string.name_required)
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) validateName()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

}