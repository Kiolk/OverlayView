package com.github.kiolk.overlayview.ui.screens.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GridsView : View {
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

    private var topCenterX: Float = 50f
    private var topCenterY: Float = 0f
    private var bottomCenterX: Float = 50f
    private var bottomCenterY: Float = 50f
    private var leftCenterX: Float = 0f
    private var leftCenterY: Float = 50f
    private var rightCenterX: Float = 50f
    private var rightCenterY: Float = 50f
    private var paint: Paint = Paint().apply {
        this.color = Color.YELLOW
        this.strokeWidth = 5f
    }
    var showCentralVentralGrid: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    var showCentralHorizontalGrid: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    private fun init(attrs: AttributeSet?, defStyle: Int) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        topCenterX = (this.right - this.left) / 2F
        topCenterY = 0f
        bottomCenterX = (this.right - this.left) / 2F
        bottomCenterY = this.bottom.toFloat()
        leftCenterX = 0f
        leftCenterY = (this.bottom - this.top) / 2F
        rightCenterX = this.right.toFloat()
        rightCenterY = (this.bottom - this.top) / 2F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (showCentralVentralGrid) {
            drawCentralVerticalGrid(canvas)
        }

        if (showCentralHorizontalGrid) {
            drawCenterHorizontalGrid(canvas)
        }
    }

    private fun drawCentralVerticalGrid(canvas: Canvas) =
        drawGrid(canvas, topCenterX, topCenterY, bottomCenterX, bottomCenterY)

    private fun drawCenterHorizontalGrid(canvas: Canvas) =
        drawGrid(canvas, leftCenterX, leftCenterY, rightCenterX, rightCenterY)

    private fun drawGrid(
        canvas: Canvas,
        startX: Float,
        startY: Float,
        stopX: Float,
        stopY: Float
    ) {
        canvas.drawLine(startX, startY, stopX, stopY, paint)
    }
}