package com.github.kiolk.overlayview.utils

private const val DEFAULT_GRID_THRESHOLD = 1

fun Int.isCloseTo(to: Int, threshold: Int = DEFAULT_GRID_THRESHOLD): Boolean {
    val range = to - threshold..to + threshold
    return this in range
}