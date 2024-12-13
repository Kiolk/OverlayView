package com.github.kiolk.overlayview.ui.screens.view.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.github.kiolk.overlayview.R

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
                this.setBackgroundDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.light_rounded_background_semi_transparent,
                        null
                    )
                )
            } else {
                this.setBackgroundColor(Color.TRANSPARENT)
            }
        }

    private fun init(attrs: AttributeSet?, defStyle: Int) {}
}