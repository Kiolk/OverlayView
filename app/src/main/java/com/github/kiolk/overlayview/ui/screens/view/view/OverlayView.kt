package com.github.kiolk.overlayview.ui.screens.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.github.kiolk.overlayview.R
import logcat.asLog
import logcat.logcat

class OverlayView : FrameLayout {

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

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.OverlayView, defStyle, 0
        )

        a.recycle()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
    }

    fun addImageFromAssets(name: String) {
        val imageDrawable = loadImageFromAssets(name) ?: return

        val image = ImageView(context)
        image.setImageDrawable(imageDrawable)
        addView(image)
    }

    private fun loadImageFromAssets(name: String): Drawable? {
        return try {
            val assetManager = context.assets
            val inputStream = assetManager.open(name)
            val drawable = Drawable.createFromStream(inputStream, null)
            inputStream.close()
            drawable
        } catch (exception: Exception) {
            logcat { exception.asLog() }
            null
        }
    }
}