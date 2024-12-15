package com.github.kiolk.overlayview.ui.screens.compasable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.kiolk.overlayview.ui.screens.compasable.components.OverlayComposable
import com.github.kiolk.overlayview.ui.screens.compasable.components.OverlaysBottomSheet
import com.github.kiolk.overlayview.ui.screens.view.OverlayViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverlayScreen(modifier: Modifier) {
    val viewModel: OverlayViewModel = koinViewModel()

    val overlayUi by viewModel.overlayUi.collectAsState(initial = null)
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isBottomSheetOpen = remember { mutableStateOf(false) }
    val overlaysBottomSheetState = rememberModalBottomSheetState()

    if (isBottomSheetOpen.value) {
        ModalBottomSheet(
            sheetState = overlaysBottomSheetState,
            onDismissRequest = { isBottomSheetOpen.value = false }) {
            OverlaysBottomSheet(categories, selectedCategory, {
                viewModel.onCategoryClick(it)
            }, onOverlayClick = {
                viewModel.onOverlayClick(it)
                isBottomSheetOpen.value = false
            })
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        OverlayComposable(overlayUi)
        IconButton(
            onClick = {
                isBottomSheetOpen.value = true
            },
            modifier = Modifier
                .background(color = Color.Gray, shape = RoundedCornerShape(size = 8.dp))
                .align(Alignment.BottomCenter),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Filled.Add,
                contentDescription = "Add image",
                tint = Color.White,
            )
        }
    }
}