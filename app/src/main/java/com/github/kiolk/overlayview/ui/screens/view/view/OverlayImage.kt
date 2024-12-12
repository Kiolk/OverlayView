package com.github.kiolk.overlayview.ui.screens.view.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet

class OverlayImage : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    var isSelectedImage: Boolean = false
        set(value) {
            field = value
            if (value) {
                this.setBackgroundColor(Color.RED)
            } else {
                this.setBackgroundColor(Color.TRANSPARENT)
            }
        }

    private fun init(attrs: AttributeSet?, defStyle: Int) {}
}