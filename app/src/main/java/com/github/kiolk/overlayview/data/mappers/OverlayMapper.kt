package com.github.kiolk.overlayview.data.mappers

import com.github.kiolk.overlayview.data.model.OverlayApi
import com.github.kiolk.overlayview.domain.model.Overlay

fun OverlayApi.toOverlay(): Overlay {
    return Overlay(
        id = id,
        overlayName = overlayName ?: "",
        createdAt = createdAt ?: "",
        categoryId = categoryId ?: 0,
        sourceUrl = sourceUrl ?: "",
        isPremium = isPremium ?: false,
    )
}