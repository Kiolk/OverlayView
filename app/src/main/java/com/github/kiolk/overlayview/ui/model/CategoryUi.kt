package com.github.kiolk.overlayview.ui.model

import com.github.kiolk.overlayview.domain.model.Overlay

data class CategoryUi(
    val title: String,
    val items: List<Overlay>,
    val thumbnailUrl: String,
    val isSelected: Boolean = false,
)