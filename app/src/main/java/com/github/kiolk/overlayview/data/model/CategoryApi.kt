package com.github.kiolk.overlayview.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryApi(
    @SerialName("title") val title: String? = null,
    @SerialName("items") val items: List<OverlayApi>? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
)