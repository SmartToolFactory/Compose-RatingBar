package com.smarttoolfactory.ratingbar.model

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween


sealed class RateChangeStrategy {
    data object InstantChange : RateChangeStrategy()
    data class AnimatedChange(
        val animationSpec: AnimationSpec<Float> = tween(300, easing = LinearEasing)
    ) : RateChangeStrategy()
}
