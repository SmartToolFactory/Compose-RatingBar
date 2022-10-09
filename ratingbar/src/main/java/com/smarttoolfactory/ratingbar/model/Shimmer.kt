package com.smarttoolfactory.ratingbar.model

import androidx.compose.animation.core.*
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.smarttoolfactory.ratingbar.DefaultColor

@Immutable
internal data class ShimmerData(
    val colors: List<Color>,
    val progress: Float,
)

/**
 * Shimmer for rating items
 * @param colors colors that are displayed on brush
 * @param animationSpec [InfiniteRepeatableSpec] to set animation and repeat modes either repeat
 * or reverse.
 */
@Immutable
data class Shimmer(
    val colors: List<Color> = listOf(
        DefaultColor.copy(alpha = .9f),
        DefaultColor.copy(alpha = .3f),
        DefaultColor.copy(alpha = .9f)
    ),
    val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        RepeatMode.Reverse
    )
) {

    companion object {
        operator fun invoke(
            color: Color,
            animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
                tween(
                    durationMillis = 3000,
                    easing = FastOutSlowInEasing
                ),
                RepeatMode.Reverse
            )
        ): Shimmer {
            return Shimmer(
                listOf(
                    color.copy(alpha = .9f),
                    color.copy(alpha = .3f),
                    color.copy(alpha = .9f)
                ),
                animationSpec
            )
        }
    }
}