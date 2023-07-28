package com.smarttoolfactory.composeratingbar.demo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.composeratingbar.R
import com.smarttoolfactory.ratingbar.DefaultColor
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.RatingInterval
import com.smarttoolfactory.ratingbar.model.Shimmer

val rateColor = Color(0xffFFA000)

@Composable
fun RatingbarDemo() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var rating by remember { mutableStateOf(3.7f) }
        var rating2 by remember { mutableStateOf(3.7f) }
        var rating3 by remember { mutableStateOf(2.3f) }
        var rating4 by remember { mutableStateOf(4.5f) }
        var rating5 by remember { mutableStateOf(1.7f) }
        var rating6 by remember { mutableStateOf(5f) }

        val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
        val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)

        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Rating Intervals",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(text = "Full")
            RatingBar(
                rating = rating,
                space = 2.dp,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                animationEnabled = true,
                gestureEnabled = true,
                ratingInterval = RatingInterval.Full,
                itemSize = 60.dp
            ) {
                rating = it
            }
            Text(text = "Half")
            RatingBar(
                rating = rating,
                space = 2.dp,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                animationEnabled = true,
                gestureEnabled = true,
                ratingInterval = RatingInterval.Half,
                itemSize = 60.dp
            ) {
                rating = it
            }
            Text(text = "Unconstrained")
            RatingBar(
                rating = rating,
                space = 2.dp,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                animationEnabled = true,
                gestureEnabled = true,
                ratingInterval = RatingInterval.Unconstrained,
                itemSize = 60.dp
            ) {
                rating = it
            }

            Text(
                "Rating: $rating",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Slider(value = rating, onValueChange = { rating = it }, valueRange = 0f..5f)

            Text(text = "animationEnabled, gestureDisabled")

            RatingBar(
                rating = rating2,
                imageVectorEmpty = Icons.Filled.Star,
                imageVectorFilled = Icons.Filled.Star,
                animationEnabled = false,
                gestureEnabled = false,
                tintEmpty = Color(0xffBDBDBD),
                shimmer = Shimmer(
                    colors = listOf(
                        rateColor.copy(.9f),
                        rateColor.copy(.3f),
                        rateColor.copy(.9f)
                    )
                ),
                itemSize = 40.dp
            ) {
                rating2 = it
            }

            Slider(
                value = rating2,
                onValueChange = { rating2 = it },
                valueRange = 0f..5f
            )

            Text(
                "Rating: $rating2",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )

            RatingBar(
                rating = rating3,
                painterEmpty = painterResource(id = R.drawable.star_background),
                painterFilled = painterResource(id = R.drawable.star_foreground),
                tintEmpty = Color(0xff795548),
                tintFilled = Color(0xff795548),
                animationEnabled = true,
                itemSize = 60.dp
            ) {
                rating3 = it
            }

            val pink500 = Color(0xffE91E63)
            RatingBar(
                rating = rating4,
                space = 14.dp,
                imageVectorEmpty = Icons.Default.FavoriteBorder,
                imageVectorFilled = Icons.Default.Favorite,
                shimmer = Shimmer(
                    color = pink500,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                ),
                tintEmpty = pink500,
                tintFilled = pink500,
                itemSize = 40.dp
            ) {
                rating4 = it
            }

            RatingBar(
                rating = rating4,
                space = 2.dp,
                imageVectorEmpty = Icons.Default.FavoriteBorder,
                imageVectorFilled = Icons.Default.Favorite,
                shimmer = Shimmer(
                    color = pink500,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    drawBorder = true
                ),
                tintEmpty = pink500,
                tintFilled = pink500,
                itemSize = 40.dp
            ) {
                rating4 = it
            }

            RatingBar(
                rating = rating5,
                space = 2.dp,
                imageVectorEmpty = ImageVector.vectorResource(id = R.drawable.outline_wb_cloudy_24),
                imageVectorFilled = ImageVector.vectorResource(id = R.drawable.baseline_wb_cloudy_24),
                tintEmpty = Color(0xff2196F3),
                tintFilled = Color(0xff4FC3F7),
                itemSize = 60.dp
            ) {
                rating5 = it
            }

            RatingBar(
                rating = rating6,
                imageVectorEmpty = ImageVector.vectorResource(id = R.drawable.twotone_person_24),
                imageVectorFilled = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
                tintEmpty = Color(0xff795548),
                tintFilled = Color(0xffA1887F),
                itemSize = 40.dp
            ) {
                rating6 = it
            }

            RatingBar(
                rating = 3.2f,
                itemCount = 5,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                tintFilled = DefaultColor
            ){

            }
            Spacer(modifier = Modifier.height(10.dp))

            RatingBar(
                rating = 4.5f,
                space = 2.dp,
                itemCount = 10,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                shimmer = Shimmer()
            ){}

            Spacer(modifier = Modifier.height(10.dp))

            RatingBar(
                rating = 8.3f,
                space = 4.dp,
                itemCount = 10,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                shimmer = Shimmer(
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 3000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    drawBorder = true
                )
            ){}
        }
    }
}
