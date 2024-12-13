package com.github.kiolk.overlayview.data.datasources

import com.github.kiolk.overlayview.data.model.CategoryApi
import retrofit2.http.GET

interface OverlayService {

    @GET("scrl/test/overlays")
    suspend fun getOverlays(): List<CategoryApi>
}