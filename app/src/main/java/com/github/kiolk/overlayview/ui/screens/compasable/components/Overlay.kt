package com.github.kiolk.overlayview.ui.screens.compasable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.github.kiolk.overlayview.domain.model.Overlay
import com.github.kiolk.overlayview.ui.theme.OverlayViewTheme

@Composable
fun Overlay(overlay: Overlay, modifier: Modifier) {
    val image = rememberAsyncImagePainter(overlay.sourceUrl)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(modifier = Modifier.fillMaxWidth(), painter = image, contentDescription = "Image")
    }
}

@Preview(showBackground = true)
@Composable
fun Overlay2Preview() {
    OverlayViewTheme {
        Overlay(Overlay(0, "", "", 0, "", false), Modifier)
    }
}