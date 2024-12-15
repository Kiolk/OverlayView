package com.github.kiolk.overlayview.ui.screens.compasable.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.kiolk.overlayview.ui.theme.OverlayViewTheme

@Composable
fun GridComponent(startX: Float, startY: Float, endX: Float, endY: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLine(
            color = Color.Yellow,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 2f
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GridPreview() {
    OverlayViewTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            GridComponent(100f, 0f, 100f, 800f)
        }
    }
}