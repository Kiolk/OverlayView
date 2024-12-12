package com.github.kiolk.overlayview.ui.screens.view.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.github.kiolk.overlayview.R
import com.github.kiolk.overlayview.utils.isCloseTo
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

    private var image: OverlayImage? = null
    private lateinit var gridsLayout: GridsView

    private var imageX: Float = 0f
    private var imageY: Float = 0f
    private var imageTouchXOffset: Float = 0f
    private var imageTouchYOffset: Float = 0f
    private var isTouched: Boolean = false
    private var isMoveAction: Boolean = false

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.OverlayView, defStyle, 0
        )

        a.recycle()
        gridsLayout = GridsView(context)
        gridsLayout.elevation = 1f
        this.addView(gridsLayout)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isTouchInsideImage(image, event)) {
                    isTouched = true
                }
            }

            MotionEvent.ACTION_MOVE -> {
                isMoveAction = true
                if (isTouched && image?.isSelectedImage == true) {
                    image?.x = event.x - imageX - imageTouchXOffset
                    image?.y = event.y - imageY - imageTouchYOffset
                    checkDisplayGrids()
                }
            }

            MotionEvent.ACTION_UP -> {
                isTouched = false
                if (!isMoveAction) {
                    if (isTouchInsideImage(image, event)) {
                        image?.isSelectedImage = !(image?.isSelectedImage ?: false)
                    } else {
                        image?.isSelectedImage = false
                    }
                }
                isMoveAction = false
                checkDisplayGrids()
            }
        }
        return true
    }

    private fun checkDisplayGrids() {
        val image = image ?: return
        val rootX = this.width / 2 / 10
        val rootY = this.height / 2 / 10
        val centerX = (image.x + image.width / 2).toInt() / 10
        val centerY = (image.y + image.height / 2).toInt() / 10

        gridsLayout.showCentralVentralGrid = centerX.isCloseTo(rootX) && image.isSelectedImage
        gridsLayout.showCentralHorizontalGrid = centerY.isCloseTo(rootY) && image.isSelectedImage
    }

    private fun isTouchInsideImage(image: OverlayImage?, event: MotionEvent): Boolean {
        image ?: return true
        val rect = Rect()
        image.getHitRect(rect)
        imageTouchXOffset = event.x - rect.left
        imageTouchYOffset = event.y - rect.top
        return rect.contains(event.x.toInt(), event.y.toInt())
    }

    fun addImageFromAssets(name: String) {
        val imageDrawable = loadImageFromAssets(name) ?: return

        image = OverlayImage(context)
        image?.let { resizeImage(it, DEFAULT_RESIZE_IMAGE_RATIO) }
        image?.isFocusable = true
        image?.setImageDrawable(imageDrawable)
        addView(image)
        imageX = image?.x ?: 0f
        imageY = image?.y ?: 0f
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

    private fun resizeImage(image: OverlayImage, ratio: Float) {
        image.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width = image.width
                val height = image.height
                image.viewTreeObserver.removeOnGlobalLayoutListener(this)

                if (width > 0 && height > 0) {
                    val params = image.layoutParams as ViewGroup.LayoutParams
                    params.width = (height * ratio).toInt()
                    params.height = (width * ratio).toInt()
                    image.layoutParams = params
                }
            }
        })
    }

    companion object {
        const val DEFAULT_RESIZE_IMAGE_RATIO = 0.2f
    }
}