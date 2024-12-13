package com.github.kiolk.overlayview.domain.model

data class Overlay(
    val id: Int,
    val overlayName: String, 
    val createdAt: String, 
    val categoryId: Int, 
    val sourceUrl: String, 
    val isPremium: Boolean,
)