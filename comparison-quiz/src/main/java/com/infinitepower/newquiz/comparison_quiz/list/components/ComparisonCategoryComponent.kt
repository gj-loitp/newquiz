package com.infinitepower.newquiz.comparison_quiz.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.infinitepower.newquiz.core.R
import com.infinitepower.newquiz.core.common.annotation.compose.PreviewNightLight
import com.infinitepower.newquiz.core.theme.NewQuizTheme
import com.infinitepower.newquiz.model.comparison_quiz.ComparisonQuizCategory

@Composable
internal fun ComparisonCategoryComponent(
    modifier: Modifier = Modifier,
    category: ComparisonQuizCategory,
    onClick: () -> Unit = {}
) {
    ComparisonCategoryComponent(
        modifier = modifier,
        title = category.title,
        imageUrl = category.imageUrl,
        onClick = onClick
    )
}

@Composable
internal fun ComparisonCategoryComponent(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    onClick: () -> Unit = {}
) {
    val shapeMedium = MaterialTheme.shapes.medium

    val containerOverlayColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    }

    Surface(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth(),
        shape = shapeMedium,
        onClick = onClick
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.image_category_of_s, title),
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(containerOverlayColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }
    }
}

@Composable
@PreviewNightLight
private fun ComparisonCategoryPreview() {
    NewQuizTheme {
        Surface {
            ComparisonCategoryComponent(
                title = "Title",
                imageUrl = "",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
