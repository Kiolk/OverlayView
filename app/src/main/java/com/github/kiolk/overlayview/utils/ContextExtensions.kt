package com.github.kiolk.overlayview.utils

import android.content.Context
import android.content.res.Configuration

fun Context.isHorizontal(): Boolean {
    val currentOrientation = resources.configuration.orientation
    return when (currentOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            true
        }

        else -> false
    }
}