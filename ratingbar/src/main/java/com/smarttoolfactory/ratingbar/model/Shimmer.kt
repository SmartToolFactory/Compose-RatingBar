package com.smarttoolfactory.ratingbar.model

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.smarttoolfactory.ratingbar.DefaultColor

@Immutable
internal data class ShimmerData(
    val fillColors: List<Color>,
    val progress: Float,
    val drawBorder: Boolean = false,
    val borderColors: List<Color>? = null
)

/**
 * Shimmer for rating items
 * @param fillColors colors that are displayed on brush that fills items
 * @param animationSpec [InfiniteRepeatableSpec] to set animation and repeat modes either repeat
 * or reverse.
 * @param drawBorder when set to true draws empty rating item above shimmer. This looks good
 * with empty items with borders with small stroke widths
 * @param borderColors colors that drawn as border when [drawBorder] is set true
 */
@Immutable
data class Shimmer(
    val fillColors: List<Color> = listOf(
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
    val drawBorder: Boolean = false,
    val borderColors: List<Color>? = null
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
                fillColors = listOf(
                    color.copy(alpha = .9f),
                    color.copy(alpha = .3f),
                    color.copy(alpha = .9f)
                ),
                animationSpec = animationSpec,
                drawBorder = drawBorder,
                borderColors = null
            )
        }
    }
}
