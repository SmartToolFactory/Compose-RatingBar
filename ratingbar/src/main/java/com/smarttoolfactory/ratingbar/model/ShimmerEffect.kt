package com.smarttoolfactory.ratingbar.model

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ShimmerEffect(
    val fillShimmer: FillShimmer? = FillShimmer(),
    val borderShimmer: BorderShimmer? = null
)

@Immutable
data class FillShimmer(
    val colors: List<Color> = DefaultFillGradientColors,
    val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        RepeatMode.Reverse
    ),
    val solidBorder: Boolean = false
)

@Immutable
data class BorderShimmer(
    val colors: List<Color>,
    val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        RepeatMode.Restart
    )
)

@Immutable
internal data class ShimmerData(
    val fillProgress: Float? = null,
    val fillColors: List<Color>? = null,
    val solidBorderOverFill: Boolean,
    val borderProgress: Float? = null,
    val borderColors: List<Color>? = null
)
