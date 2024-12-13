package com.github.kiolk.overlayview.domain.repository

import com.github.kiolk.overlayview.domain.model.Category

interface OverlayRepository {

    suspend fun getOverlays(): List<Category>
}