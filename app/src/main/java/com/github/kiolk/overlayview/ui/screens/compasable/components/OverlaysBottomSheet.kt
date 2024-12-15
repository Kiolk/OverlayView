package com.github.kiolk.overlayview.ui.screens.compasable.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.kiolk.overlayview.domain.model.Overlay
import com.github.kiolk.overlayview.ui.model.CategoryUi

@Composable
fun OverlaysBottomSheet(
    categories: List<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategoryClick: (CategoryUi) -> Unit,
    onOverlayClick: (Overlay) -> Unit,
) {
    Column {
        LazyRow {
            items(categories) {
                Category(it, Modifier.clickable {
                    onCategoryClick(it)
                })
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedCategory?.items ?: emptyList()) {
                Overlay(it, Modifier.clickable {
                    onOverlayClick(it)
                })
            }
        }
    }
}
