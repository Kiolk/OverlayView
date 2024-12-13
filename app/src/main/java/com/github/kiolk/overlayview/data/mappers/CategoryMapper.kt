package com.github.kiolk.overlayview.data.mappers

import com.github.kiolk.overlayview.data.model.CategoryApi
import com.github.kiolk.overlayview.domain.model.Category

fun CategoryApi.toCategory(): Category {
    return Category(
        title = title.orEmpty(),
        items = items?.map { it.toOverlay() }.orEmpty(),
        thumbnailUrl = thumbnailUrl.orEmpty(),
    )
}