package com.smarttoolfactory.composeratingbar.demo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composeratingbar.R
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.smarttoolfactory.ratingbar.model.RatingInterval

@Preview
@Composable
private fun Test() {
    Column(
        Modifier.fillMaxSize()
    ) {
        var rating by remember { mutableStateOf(3.7f) }

        val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
        val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)

        Column(modifier = Modifier.fillMaxWidth()) {
            RatingBar(
                modifier = Modifier
                    .border(1.dp, Color.Blue)
                    .fillMaxSize()
                ,
                rating = rating,
                space = 2.dp,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                rateChangeStrategy = RateChangeStrategy.InstantChange,
                ratingInterval = RatingInterval.Unconstrained,
                itemSize = 50.dp
            ) {
                rating = it
            }
        }
    }
}