package com.github.kiolk.overlayview.domain.model

data class Category(
    val title: String,
    val items: List<Overlay>,
    val thumbnailUrl: String,
)