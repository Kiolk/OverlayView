package com.github.kiolk.overlayview.data.datasources

import com.github.kiolk.overlayview.domain.model.Category

interface OverlayDataSource {

    suspend fun getOverlays(): List<Category>

    suspend fun saveOverlays(overlays: List<Category>)
}