package com.github.kiolk.overlayview.ui.model

import com.github.kiolk.overlayview.domain.model.Overlay
import java.io.File

data class OverlayUi(
    val id: Int,
    val overlay: Overlay,
    val path: File,
)
