package com.github.kiolk.overlayview.data.repository

import com.github.kiolk.overlayview.data.datasources.OverlayDataSource
import com.github.kiolk.overlayview.domain.model.Category
import com.github.kiolk.overlayview.domain.repository.OverlayRepository

class OverlayRepositoryImpl(
    private val remoteDataSource: OverlayDataSource,
    private val localDataSource: OverlayDataSource,
) : OverlayRepository {

    override suspend fun getOverlays(): List<Category> {
        var overlays = localDataSource.getOverlays()

        if (overlays.isEmpty()) {
            overlays = remoteDataSource.getOverlays()
            localDataSource.saveOverlays(overlays)
        }

        return overlays
    }
}