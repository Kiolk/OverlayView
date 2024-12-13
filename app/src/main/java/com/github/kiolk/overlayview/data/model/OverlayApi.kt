package com.github.kiolk.overlayview.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverlayApi(
    val id: Int,
    @SerialName("overlay_name") val overlayName: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("category_id") val categoryId: Int? = null,
    @SerialName("source_url") val sourceUrl: String? = null,
    @SerialName("is_premium") val isPremium: Boolean? = null,
)
