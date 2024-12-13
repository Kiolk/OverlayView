package com.github.kiolk.overlayview.data.datasources

import com.github.kiolk.overlayview.domain.model.Category

class LocalOverlayDataSource: OverlayDataSource {

    // TODO Implement logic for storing data in local DB
    private var overlays: List<Category> = emptyList()

    override suspend fun getOverlays(): List<Category> {
        return overlays
    }

    override suspend fun saveOverlays(overlays: List<Category>) {
        this.overlays = overlays
    }
}
