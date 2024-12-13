package com.github.kiolk.overlayview.ui.mappers

import com.github.kiolk.overlayview.domain.model.Category
import com.github.kiolk.overlayview.ui.model.CategoryUi

fun Category.toCategoryUi(isSelected: Boolean = false): CategoryUi {
    return CategoryUi(
        title = this.title,
        items = this.items,
        thumbnailUrl = this.thumbnailUrl,
        isSelected = isSelected,
    )
}