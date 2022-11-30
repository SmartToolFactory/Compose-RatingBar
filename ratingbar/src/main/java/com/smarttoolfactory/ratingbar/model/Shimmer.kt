package com.smarttoolfactory.ratingbar.model

import androidx.compose.animation.core.*
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.smarttoolfactory.ratingbar.DefaultColor

@Immutable
internal data class ShimmerData(
    val colors: List<Color>,
    val progress: Float,
    val drawBorder: Boolean = false
)

/**
 * Shimmer for rating items
 * @param colors colors that are displayed on brush
 * @param animationSpec [InfiniteRepeatableSpec] to set animation and repeat modes either repeat
 * or reverse.
 * @param drawBorder when set to true draws empty rating item above shimmer. This looks good
 * with empty items with borders with small stroke widths
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
    ),
    val drawBorder: Boolean = false
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
            ),
            drawBorder: Boolean = false
        ): Shimmer {
            return Shimmer(
                colors = listOf(
                    color.copy(alpha = .9f),
                    color.copy(alpha = .3f),
                    color.copy(alpha = .9f)
                ),
                animationSpec = animationSpec,
                drawBorder = drawBorder
            )
        }
    }
}
