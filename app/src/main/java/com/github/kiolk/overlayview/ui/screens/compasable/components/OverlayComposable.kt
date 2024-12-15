package com.github.kiolk.overlayview.ui.screens.compasable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberAsyncImagePainter
import com.github.kiolk.overlayview.ui.model.OverlayUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun OverlayComposable(overlayUi: OverlayUi?) {

    val viewModel: OViewModel = koinViewModel()

    val images by viewModel.images.collectAsState()
    val grids by viewModel.grids.collectAsState()

    overlayUi?.let { viewModel.addImage(it.path.path, it.id) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.DarkGray)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    viewModel.onTap(offset)
                }
            }
            .onGloballyPositioned { coordinates ->
                viewModel.addOverlyBound(coordinates.size.toSize())
            }
    ) {
        images.forEach {
            Box(modifier = Modifier
                .offset {
                    IntOffset(
                        it.offset.x.toInt(),
                        it.offset.y.toInt()
                    )
                }
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        viewModel.onScaleImage(it.id, zoom)
                        viewModel.onRotateImage(it.id, rotation)
                        viewModel.onDrag(it.id, pan)
                    }
                }
                .onGloballyPositioned { coordinates ->
                    viewModel.addBound(it.id, coordinates.boundsInParent())
                }
                // TODO One is possible ways to handle zoom and rotation image. Commented for future implementation
//                .graphicsLayer {
//                    scaleX = it.zoom
//                    scaleY = it.zoom
//                    rotationZ = it.rotation
//                }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it.path),
                    contentDescription = "Image",
                    modifier = Modifier
                        .background(
                            color = if (it.isSelected) Color.Gray.copy(alpha = 0.5f) else Color.Transparent,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                )
            }
        }
        grids.forEach {
            GridComponent(it.start.x, it.start.y, it.end.x, it.end.y)
        }
    }
}