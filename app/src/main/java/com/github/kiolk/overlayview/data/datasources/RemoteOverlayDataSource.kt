package com.github.kiolk.overlayview.data.datasources

import com.github.kiolk.overlayview.data.mappers.toCategory
import com.github.kiolk.overlayview.domain.model.Category

class RemoteOverlayDataSource(private val overlayService: OverlayService): OverlayDataSource {
    override suspend fun getOverlays(): List<Category> {
        return overlayService.getOverlays().map { it.toCategory() }
    }

    override suspend fun saveOverlays(overlays: List<Category>) = throw NotImplementedError()
}