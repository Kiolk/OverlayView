package com.github.kiolk.overlayview.ui.screens.compasable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.github.kiolk.overlayview.ui.model.CategoryUi
import com.github.kiolk.overlayview.ui.theme.OverlayViewTheme

@Composable
fun Category(category: CategoryUi, modifier: Modifier) {
    val image = rememberAsyncImagePainter(category.thumbnailUrl)

    Box(
        modifier = modifier
            .size(100.dp, 100.dp)
            .background(
                shape = RoundedCornerShape(4.dp),
                color = if (category.isSelected) Color.Gray else Color.Transparent
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                painter = image,
                contentDescription = "Image",
                contentScale = ContentScale.Inside
            )
            Text(category.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPreview(@PreviewParameter(CategoryPreviewProvider::class) category: CategoryUi) {
    OverlayViewTheme {
        Category(category, Modifier)
    }
}

class CategoryPreviewProvider : PreviewParameterProvider<CategoryUi> {
    override val values = sequenceOf(
        CategoryUi(
            title = "Title",
            items = emptyList(),
            isSelected = false,
            thumbnailUrl = "https://scrl-addtext.b-cdn.net/1707669886150-s1.png"
        ),

        CategoryUi(
            title = "Title",
            items = emptyList(),
            isSelected = true,
            thumbnailUrl = ""
        )
    )
}