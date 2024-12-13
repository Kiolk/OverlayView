package com.github.kiolk.overlayview.ui.screens.view.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
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
import java.io.File

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

    private var selectedImage: OverlayImage? = null
    private var addedImages: MutableList<OverlayImage> = mutableListOf()
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
                if (isTouchInsideImage(selectedImage, event)) {
                    isTouched = true
                }
            }

            MotionEvent.ACTION_MOVE -> {
                isMoveAction = true
                if (isTouched && selectedImage?.isSelectedImage == true) {
                    selectedImage?.x = event.x - imageX - imageTouchXOffset
                    selectedImage?.y = event.y - imageY - imageTouchYOffset
                    checkDisplayGrids()
                }
            }

            MotionEvent.ACTION_UP -> {
                isTouched = false
                if (!isMoveAction) {
                    if (isTouchInsideImage(selectedImage, event)) {
                        selectedImage?.isSelectedImage = !(selectedImage?.isSelectedImage ?: false)
                    } else {
                        selectedImage?.isSelectedImage = false
                    }
                }

                if (selectedImage?.isSelectedImage == false) {
                    selectedImage = null
                }
                isMoveAction = false
                checkDisplayGrids()
            }
        }
        return true
    }

    private fun checkDisplayGrids() {
        if (selectedImage == null) {
            gridsLayout.showCentralVentralGrid = false
            gridsLayout.showCentralHorizontalGrid = false
            gridsLayout.drawHorizontal(emptyList())
            gridsLayout.drawVertical(emptyList())
            return
        }

        val image = selectedImage ?: return
        val rootX = this.width / 2
        val rootY = this.height / 2
        val centerX = (image.x + image.width / 2).toInt()
        val centerY = (image.y + image.height / 2).toInt()

        gridsLayout.showCentralVentralGrid = centerX.isCloseTo(rootX) && image.isSelectedImage
        gridsLayout.showCentralHorizontalGrid = centerY.isCloseTo(rootY) && image.isSelectedImage


        val verticalLines = mutableListOf<Int>()

        verticalLines.addAll(getMatch(((selectedImage?.x ?: 0).toInt())))
        verticalLines.addAll(
            getMatch(
                ((selectedImage?.x ?: 0).toInt() + (selectedImage?.width ?: 0))
            )
        )

        gridsLayout.drawVertical(verticalLines)

        val horizontalLines = mutableListOf<Int>()

        horizontalLines.addAll(getMatch(((selectedImage?.y ?: 0).toInt()), isVertical = false))
        horizontalLines.addAll(
            getMatch(
                ((selectedImage?.y ?: 0).toInt() + (selectedImage?.height ?: 0)),
                isVertical = false,
            )
        )

        gridsLayout.drawHorizontal(horizontalLines)
    }

    private fun getMatch(verticalValue: Int, isVertical: Boolean = true): List<Int> {
        val hasMatch = addedImages.any {
            val startPoint = if (isVertical) it.x.toInt() else it.y.toInt()
            val endPoint = if (isVertical) it.width else it.height

            it != selectedImage && ((startPoint + endPoint).isCloseTo(verticalValue) || startPoint
                .isCloseTo(verticalValue))
        }

        return if (hasMatch) {
            return listOf(verticalValue)
        } else {
            emptyList()
        }
    }

    private fun isTouchInsideImage(image: OverlayImage?, event: MotionEvent): Boolean {
        if (image == null) {
            selectImage(event.x, event.y)
        }

        val rect = Rect()
        image?.getHitRect(rect) ?: return false
        imageTouchXOffset = event.x - rect.left
        imageTouchYOffset = event.y - rect.top
        return isInsideImage(image, event.x, event.y)
    }

    private fun isInsideImage(image: OverlayImage, x: Float, y: Float): Boolean {
        val rect = Rect()
        image.getHitRect(rect)
        return rect.contains(x.toInt(), y.toInt())
    }

    private fun selectImage(x: Float, y: Float): Boolean {
        val image = addedImages.lastOrNull {
            isInsideImage(it, x, y)
        }
        selectedImage = image
        return image != null
    }

    fun addImageFromFile(file: File) {
        loadImageFromFile(file)?.let { addImage(it) }
    }

    fun addImageFromAssets(name: String) {
        loadImageFromAssets(name)?.let { addImage(it) }
    }

    private fun addImage(drawable: Drawable) {
        val newImage = OverlayImage(context)
        resizeImage(newImage, DEFAULT_RESIZE_IMAGE_RATIO)
        newImage.isFocusable = true
        newImage.setImageDrawable(drawable)
        addView(newImage)
        addedImages.add(newImage)
        newImage.x += addedImages.size * DEFAULT_OFFSET
        newImage.y += addedImages.size * DEFAULT_OFFSET
    }

    private fun loadImageFromFile(file: File): Drawable? {
        return try {
            val bitmap = BitmapFactory.decodeFile(file.path)
            if (bitmap != null) {
                BitmapDrawable(context.resources, bitmap)
            } else {
                null
            }
        } catch (e: Exception) {
            logcat { e.asLog() }
            null
        }
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
        const val DEFAULT_OFFSET = 10
    }
}