package com.github.kiolk.overlayview.ui.screens.compasable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.kiolk.overlayview.ui.theme.OverlayViewTheme

@Composable
fun OverlayComposable(modifier: Modifier) {
    Text("Overlay Composable")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OverlayPreview() {
    OverlayViewTheme {
        OverlayComposable(Modifier)
    }
}