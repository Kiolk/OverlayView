package com.github.kiolk.overlayview.ui.screens.compasable.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import com.github.kiolk.overlayview.utils.isCloseTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList

class OViewModel : ViewModel() {

    private var overlaySize: Size = Size.Zero
    private val imagesPool: MutableMap<Int, OImage> = mutableMapOf()
    private var selectedImage: Int? = null

    private val _images = MutableStateFlow<List<OImage>>(emptyList())
    val images = _images.asStateFlow()

    private val _grids = MutableStateFlow<List<Grid>>(emptyList())
    val grids = _grids.asStateFlow()

    fun addImage(image: String, index: Int) {
        if (!imagesPool.containsKey(index)) {
            imagesPool[index] =
                OImage(index, image, 0f, 0f, false, Offset.Zero, Rect.Zero)
            _images.value = imagesPool.values.toList()
        }
    }

    fun onDrag(path: Int, dragAmount: Offset) {
        val image = imagesPool[path] ?: return
        if (image.isSelected) {
            imagesPool[path] = image.copy(offset = image.offset + dragAmount)
            _images.value = imagesPool.values.toList()
            checkDrawGrids()
        }
    }

    fun addBound(path: Int, boundsInParent: Rect) {
        val image = imagesPool[path] ?: return
        imagesPool[path] = image.copy(bounds = boundsInParent)
        _images.value = imagesPool.values.toList()
    }

    fun onTap(offset: Offset) {
        val selected = imagesPool.values.lastOrNull {
            it.bounds.contains(offset)
        }
        imagesPool.keys.forEach {
            if (selected?.id == it) {
                imagesPool[it] = imagesPool[it]!!.copy(isSelected = !selected.isSelected)
            } else {
                imagesPool[it] = imagesPool[it]!!.copy(isSelected = false)
            }
        }
        _images.value = imagesPool.values.toList()
        selectedImage = imagesPool.values.firstOrNull { it.isSelected }?.id
        checkDrawGrids()
    }

    fun addOverlyBound(toSize: Size) {
        overlaySize = toSize
    }

    private fun checkDrawGrids() {
        val image = imagesPool[selectedImage]

        if (image == null) {
            _grids.value = emptyList()
            return
        }

        val grids: MutableList<Grid> = mutableListOf()


        checkCenterHorizontal(image.getCenterY())?.let { grids.add(it) }
        checkCenterVertical(image.getCenterX())?.let { grids.add(it) }
        checkVerticalBounds(image.getLeft())?.let { grids.add(it) }
        checkVerticalBounds(image.getRight())?.let { grids.add(it) }
        checkHorizontalBounds(image.getTop())?.let { grids.add(it) }
        checkHorizontalBounds(image.getBottom())?.let {
            grids.add(it)
        }

        _grids.value = grids.toImmutableList()
    }

    private fun checkCenterVertical(x: Int): Grid? {
        val isDrawCentralGrid = x.isCloseTo(overlaySize.width.toInt() / 2)


        return if (isDrawCentralGrid) {
            Grid(
                Offset(overlaySize.width / 2, 0f),
                Offset(overlaySize.width / 2, overlaySize.height)
            )
        } else {
            null
        }
    }

    private fun checkCenterHorizontal(y: Int): Grid? {
        val isDrawHorizontalCentralGrid = y.isCloseTo(overlaySize.height.toInt() / 2)

        return if (isDrawHorizontalCentralGrid) {
            Grid(
                Offset(0f, overlaySize.height / 2),
                Offset(overlaySize.width, overlaySize.height / 2)
            )
        } else {
            null
        }
    }

    private fun checkVerticalBounds(x: Int): Grid? {
        val isDrawHorizontal = imagesPool.values.any {
            it.id != selectedImage && (x
                .isCloseTo(it.offset.x.toInt()) || x
                .isCloseTo(it.offset.x.toInt() + it.bounds.width.toInt())
                    )
        }

        if (isDrawHorizontal) {
            return Grid(
                Offset(x.toFloat(), 0f),
                Offset(x.toFloat(), overlaySize.height)
            )
        }

        return null
    }

    private fun checkHorizontalBounds(y: Int): Grid? {
        val isDrawHorizontal = imagesPool.values.any {
            it.id != selectedImage && (y
                .isCloseTo(it.offset.y.toInt()) || y
                .isCloseTo(it.offset.y.toInt() + it.bounds.height.toInt())
                    )
        }

        if (isDrawHorizontal) {
            return Grid(
                Offset(0f, y.toFloat()),
                Offset(overlaySize.width, y.toFloat())
            )
        }

        return null
    }

    fun onScaleImage(id: Int, zoom: Float) {
        val image = imagesPool[id] ?: return
        if (image.isSelected) {
            imagesPool[id] = image.copy(zoom = image.zoom * zoom)
            _images.value = imagesPool.values.toList()
            checkDrawGrids()
        }
    }

    fun onRotateImage(id: Int, rotation: Float) {
        val image = imagesPool[id] ?: return
        if (image.isSelected) {
            imagesPool[id] = image.copy(rotation = image.rotation + rotation)
            _images.value = imagesPool.values.toList()
            checkDrawGrids()
        }
    }
}

data class OImage(
    val id: Int,
    val path: String,
    val x: Float,
    val y: Float,
    val isSelected: Boolean,
    val offset: Offset,
    val bounds: Rect,
    val rotation: Float = 0f,
    val zoom: Float = 1f,
) {
    fun getCenterX(): Int = (offset.x + bounds.width / 2).toInt()
    fun getCenterY(): Int = (offset.y + bounds.height / 2).toInt()
    fun getLeft(): Int = offset.x.toInt()
    fun getRight(): Int = (offset.x + bounds.width).toInt()
    fun getTop(): Int = offset.y.toInt()
    fun getBottom(): Int = (offset.y + bounds.height).toInt()
}

data class Grid(
    val start: Offset,
    val end: Offset,
)