package com.github.kiolk.overlayview.ui.screens.view.view.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DefaultHorizontalItemDecorator(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == itemCount - 1) {
            outRect.top = spacing
            outRect.bottom = spacing
            outRect.right = spacing
            outRect.left = spacing
        } else {
            outRect.top = spacing
            outRect.bottom = spacing
            outRect.left = spacing
        }
    }
}