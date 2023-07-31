package com.smarttoolfactory.composeratingbar.demo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.composeratingbar.R
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.BorderShimmer
import com.smarttoolfactory.ratingbar.model.DefaultColor
import com.smarttoolfactory.ratingbar.model.FillShimmer
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.smarttoolfactory.ratingbar.model.RatingInterval
import com.smarttoolfactory.ratingbar.model.ShimmerEffect

val rateColor = Color(0xffFFA000)

@Preview
@Composable
fun RatingbarDemo() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            RatingIntervalsSample()
            RateChangeSample()
            GestureStrategySample()
            ColorAndShapeSample()
            ShimmerSample()
        }
    }
}

@Composable
private fun RatingIntervalsSample() {

    var rating by remember { mutableStateOf(3.7f) }

    val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
    val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)


    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = "Rating Intervals",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary
    )
    Text(text = "Full")
    RatingBar(
        rating = rating,
        space = 2.dp,
        imageEmpty = imageBackground,
        imageFilled = imageForeground,
        rateChangeStrategy = RateChangeStrategy.AnimatedChange(),
        ratingInterval = RatingInterval.Full,
        itemSize = 40.dp
    ) {
        rating = it
    }
    Text(text = "Half")
    RatingBar(
        rating = rating,
        space = 2.dp,
        imageEmpty = imageBackground,
        imageFilled = imageForeground,
        ratingInterval = RatingInterval.Half,
        itemSize = 40.dp
    ) {
        rating = it
    }
    Text(text = "Unconstrained")
    RatingBar(
        rating = rating,
        space = 2.dp,
        imageEmpty = imageBackground,
        imageFilled = imageForeground,
        ratingInterval = RatingInterval.Unconstrained,
        itemSize = 40.dp
    ) {
        rating = it
    }

    Text(
        "Rating: $rating",
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.primary
    )

    Slider(value = rating, onValueChange = { rating = it }, valueRange = 0f..5f)

}

@Composable
private fun RateChangeSample() {

    var rating2 by remember { mutableStateOf(3.7f) }

    val painterBackground = painterResource(id = R.drawable.star_background)
    val painterForeground = painterResource(id = R.drawable.star_foreground)


    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = "Rate Change Modes",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary
    )

    Text(text = "InstantChange")

    RatingBar(
        rating = rating2,
        space = 2.dp,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        tintFilled = rateColor,
        rateChangeStrategy = RateChangeStrategy.InstantChange,
        itemSize = 40.dp
    ) {
        rating2 = it
    }

    Text(text = "LinearEasing")
    RatingBar(
        rating = rating2,
        space = 2.dp,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        tintFilled = rateColor,
        rateChangeStrategy = RateChangeStrategy.AnimatedChange(),
        itemSize = 40.dp
    ) {
        rating2 = it
    }

    Text(text = "spring")
    RatingBar(
        rating = rating2,
        space = 2.dp,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        tintFilled = rateColor,

        rateChangeStrategy = RateChangeStrategy.AnimatedChange(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = 300f
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
}

@Composable
private fun GestureStrategySample() {

    var rating3 by remember { mutableStateOf(2.3f) }


    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = "Gesture Modes",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary
    )

    Text(text = "DragAndTouch")
    RatingBar(
        rating = rating3,
        painterEmpty = painterResource(id = R.drawable.star_background),
        painterFilled = painterResource(id = R.drawable.star_foreground),
        gestureStrategy = GestureStrategy.DragAndPress,
        tintEmpty = Color(0xff795548),
        tintFilled = Color(0xff795548),
        itemSize = 40.dp
    ) {
        rating3 = it
    }



    Text(text = "Touch")
    RatingBar(
        rating = rating3,
        painterEmpty = painterResource(id = R.drawable.star_background),
        painterFilled = painterResource(id = R.drawable.star_foreground),
        gestureStrategy = GestureStrategy.Press,
        tintEmpty = Color(0xff795548),
        tintFilled = Color(0xff795548),
        itemSize = 40.dp
    ) {
        rating3 = it
    }

    Text(text = "None")
    RatingBar(
        rating = rating3,
        painterEmpty = painterResource(id = R.drawable.star_background),
        painterFilled = painterResource(id = R.drawable.star_foreground),
        gestureStrategy = GestureStrategy.None,
        tintEmpty = Color(0xff795548),
        tintFilled = Color(0xff795548),
        itemSize = 40.dp
    ) {
        rating3 = it
    }
}

@Composable
private fun ColorAndShapeSample() {
    var rating4 by remember { mutableStateOf(4.5f) }
    var rating5 by remember { mutableStateOf(1.7f) }
    val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
    val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)


    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = "Color and Shape",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary
    )

    Text(text = "ImageBitmap")

    RatingBar(
        rating = 3.2f,
        itemCount = 5,
        imageEmpty = imageBackground,
        imageFilled = imageForeground,
        tintFilled = DefaultColor
    ) {

    }

    Text(text = "ImageVector")

    RatingBar(
        rating = rating4,
        space = 2.dp,
        imageVectorEmpty = ImageVector.vectorResource(id = R.drawable.outline_wb_cloudy_24),
        imageVectorFilled = ImageVector.vectorResource(id = R.drawable.baseline_wb_cloudy_24),
        tintEmpty = Color(0xff2196F3),
        tintFilled = Color(0xff4FC3F7),
        itemSize = 60.dp
    ) {
        rating4 = it
    }

    RatingBar(
        rating = rating5,
        imageVectorEmpty = ImageVector.vectorResource(id = R.drawable.twotone_person_24),
        imageVectorFilled = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
        tintEmpty = Color(0xff795548),
        tintFilled = Color(0xffA1887F),
        itemSize = 40.dp
    ) {
        rating5 = it
    }

    RatingBar(
        rating = rating5,
        imageVectorEmpty = Icons.Outlined.Notifications,
        imageVectorFilled = Icons.Filled.Notifications,
        tintEmpty = Color(0xff795DD48),
        tintFilled = DefaultColor,
        itemSize = 40.dp
    ) {
        rating5 = it
    }
}

@Composable
fun ShimmerSample() {
    var rating6 by remember { mutableStateOf(5f) }
    val painterBackground = painterResource(id = R.drawable.star_background)
    val painterForeground = painterResource(id = R.drawable.star_foreground)

    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = "Fill and Border Shimmer",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary
    )

    val pink500 = Color(0xffE91E63)

    val pinkColors = remember {
        listOf(
            pink500.copy(alpha = .9f),
            pink500.copy(alpha = .6f),
            pink500.copy(alpha = .9f),
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "FillShimmer with solidBorder = false")

    RatingBar(
        rating = rating6,
        space = 14.dp,
        imageVectorEmpty = Icons.Default.FavoriteBorder,
        imageVectorFilled = Icons.Default.Favorite,
        shimmerEffect = ShimmerEffect(
            FillShimmer(
                colors = pinkColors,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        ),
        tintEmpty = pink500,
        tintFilled = pink500,
        itemSize = 40.dp
    ) {
        rating6 = it
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "FillShimmer with solidBorder = true")

    RatingBar(
        rating = rating6,
        space = 2.dp,
        imageVectorEmpty = Icons.Default.FavoriteBorder,
        imageVectorFilled = Icons.Default.Favorite,
        shimmerEffect = ShimmerEffect(
            FillShimmer(
                colors = pinkColors,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                solidBorder = true
            )
        ),
        tintEmpty = pink500,
        tintFilled = pink500,
        itemSize = 40.dp
    ) {
        rating6 = it
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "FillShimmer with solidBorder = true")

    RatingBar(
        rating = 4.2f,
        space = 2.dp,
        itemCount = 5,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        shimmerEffect = ShimmerEffect(
            FillShimmer(
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                solidBorder = true
            )
        ),
        itemSize = 60.dp
    ) {}

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "BorderShimmer")

    RatingBar(
        rating = 4.2f,
        space = 2.dp,
        itemCount = 5,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        shimmerEffect = ShimmerEffect(
            fillShimmer = null,
            borderShimmer = BorderShimmer(
                colors = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Blue
                )
            )
        ),
        itemSize = 60.dp
    ) {}

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "FillShimmer and BorderShimmer")

    RatingBar(
        rating = 4.2f,
        space = 2.dp,
        itemCount = 5,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        shimmerEffect = ShimmerEffect(
            fillShimmer = FillShimmer(),
            borderShimmer = BorderShimmer(
                colors = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Blue
                )
            )
        ),
        itemSize = 60.dp
    ) {}

    Spacer(modifier = Modifier.height(10.dp))

    RatingBar(
        rating = 4.5f,
        space = 2.dp,
        itemCount = 10,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        tintEmpty = rateColor,
        shimmerEffect = ShimmerEffect(
            fillShimmer = FillShimmer(
                colors = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Blue
                )
            )
        )
    ) {}

    Spacer(modifier = Modifier.height(10.dp))

    RatingBar(
        rating = 8.3f,
        space = 4.dp,
        itemCount = 10,
        painterEmpty = painterBackground,
        painterFilled = painterForeground,
        tintEmpty = rateColor,
        shimmerEffect = ShimmerEffect(
            fillShimmer = FillShimmer(
                colors = listOf(
                    Color.Cyan,
                    Color.Green,
                    Color.Blue,
                    Color.Cyan,
                ),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart,
                ),
                solidBorder = true
            )
        )
    ) {}
}
