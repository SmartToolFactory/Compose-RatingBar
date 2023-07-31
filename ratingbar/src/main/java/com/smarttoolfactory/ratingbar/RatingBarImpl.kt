package com.smarttoolfactory.ratingbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.smarttoolfactory.ratingbar.model.RatingInterval
import com.smarttoolfactory.ratingbar.model.ShimmerData
import com.smarttoolfactory.ratingbar.model.ShimmerEffect
import com.smarttoolfactory.ratingbar.model.getRatingForInterval
import kotlinx.coroutines.launch


@Composable
internal fun RatingBarImpl(
    modifier: Modifier = Modifier,
    rating: Float,
    itemSize: Dp = Dp.Unspecified,
    intrinsicWidth: Float,
    intrinsicHeight: Float,
    rateChangeStrategy: RateChangeStrategy = RateChangeStrategy.AnimatedChange(),
    gestureStrategy: GestureStrategy = GestureStrategy.DragAndPress,
    shimmerEffect: ShimmerEffect?,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    ratingInterval: RatingInterval,
    allowZeroRating: Boolean,
    block: DrawScope.(
        rating: Float,
        space: Float,
        shimmerData: ShimmerData?,
    ) -> Unit,
    onRatingChangeFinished: ((Float) -> Unit)? = null,
    onRatingChange: (Float) -> Unit
) {

    BoxWithConstraints(
        modifier,
        contentAlignment = Alignment.Center
    ) {

        val density = LocalDensity.current

        val boxMaxWidth = constraints.maxWidth
        val boxMaxHeight = constraints.maxHeight

        // When we have non-scrollable modifier
        // limit item width at most total width/item count
        val itemWidthPx = if (constraints.hasBoundedWidth) {
            (if (itemSize != Dp.Unspecified) {
                density.run { itemSize.toPx() }
            } else {
                intrinsicWidth
            }).coerceIn(0f, (boxMaxWidth / itemCount).toFloat())
        } else {
            if (itemSize != Dp.Unspecified) {
                density.run { itemSize.toPx() }
            } else {
                intrinsicWidth
            }
        }

        // Assume max item height as max item width after it's constrained
        // by maximum width of Container
        val itemHeightPx = (if (constraints.hasBoundedHeight) {
            if (itemSize != Dp.Unspecified) {
                density.run { itemSize.toPx() }
            } else {
                intrinsicHeight
            }.coerceAtMost(boxMaxHeight.toFloat())
        } else {
            if (itemSize != Dp.Unspecified) {
                density.run { itemSize.toPx() }
            } else {
                intrinsicHeight
            }
        }).coerceIn(0f, itemWidthPx)

        // Space can be between 0f and space left after placing rating items
        // If items occupy space more than available for rating items no space is assigned
        val adjustedSpacePx = density
            .run { space.toPx() }
            .coerceIn(
                minimumValue = 0f,
                maximumValue = (constraints.maxWidth - itemWidthPx * itemCount)
                    .coerceAtLeast(0f)
            )


        val totalWidth: Dp = density.run {
            itemWidthPx.toDp() * itemCount + adjustedSpacePx.toDp() * (itemCount - 1)
        }

        val totalHeight = density.run {
            itemHeightPx.toDp()
        }

        val itemIntervals = remember {
            ratingItemPositions(itemWidthPx, adjustedSpacePx, itemCount)
        }

        val coerced = rating.coerceIn(0f, itemCount.toFloat())

        val coroutineScope = rememberCoroutineScope()
        val animatableRating = remember {
            Animatable(if (rateChangeStrategy is RateChangeStrategy.AnimatedChange) 0f else coerced)
        }

        LaunchedEffect(key1 = coerced) {
            if (rateChangeStrategy is RateChangeStrategy.AnimatedChange) {
                animatableRating.animateTo(
                    targetValue = coerced,
                    animationSpec = rateChangeStrategy.animationSpec

                )
            } else {
                animatableRating.snapTo(coerced)
            }
        }

        val gestureModifier = Modifier
            .then(
                if (gestureStrategy == GestureStrategy.DragAndPress) {
                    Modifier.pointerInput(Unit) {
                        val ratingBarWidth = size.width.toFloat()
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, _ ->
                                change.consume()

                                val x = change.position.x
                                val newRating = getRatingFromTouchPosition(
                                    x = x,
                                    itemIntervals = itemIntervals,
                                    ratingBarDimension = ratingBarWidth,
                                    space = adjustedSpacePx,
                                    totalCount = itemCount,
                                    ratingInterval = ratingInterval,
                                    allowZeroRating = allowZeroRating
                                )

                                onRatingChange.invoke(newRating)

                                coroutineScope.launch {
                                    animatableRating.snapTo(newRating)
                                }

                            },
                            onDragEnd = {
                                onRatingChangeFinished?.invoke(animatableRating.targetValue)
                            }
                        )
                    }
                } else {
                    Modifier
                }
            )
            .then(
                if (gestureStrategy != GestureStrategy.None) {
                    Modifier.pointerInput(Unit) {
                        val ratingBarWidth = size.width.toFloat()

                        detectTapGestures { change ->
                            val x = change.x
                            val newRating = getRatingFromTouchPosition(
                                x = x,
                                itemIntervals = itemIntervals,
                                ratingBarDimension = ratingBarWidth,
                                space = adjustedSpacePx,
                                totalCount = itemCount,
                                ratingInterval = ratingInterval,
                                allowZeroRating = allowZeroRating
                            )

                            onRatingChange.invoke(newRating)
                            onRatingChangeFinished?.invoke(newRating)

                            coroutineScope.launch {
                                if (rateChangeStrategy is RateChangeStrategy.AnimatedChange) {
                                    animatableRating.animateTo(
                                        targetValue = newRating,
                                        animationSpec = rateChangeStrategy.animationSpec
                                    )
                                } else {
                                    animatableRating.snapTo(newRating)
                                }
                            }
                        }
                    }
                } else {
                    Modifier
                }
            )

        val shimmerData: ShimmerData? = getShimmerData(shimmerEffect)

        Box(
            modifier = gestureModifier
                .width(totalWidth)
                .height(totalHeight)
                .drawBehind {
                    block(
                        animatableRating.value,
                        adjustedSpacePx,
                        shimmerData
                    )
                }
        )
    }
}

@Composable
private fun getShimmerData(shimmerEffect: ShimmerEffect?): ShimmerData? {
    val shimmerData: ShimmerData? = shimmerEffect?.let {

        val fillShimmer = shimmerEffect.fillShimmer
        val borderShimmer = shimmerEffect.borderShimmer

        val fillProgress = fillShimmer?.animationSpec?.let {
            getRatingShimmerProgress(fillShimmer.animationSpec)
        }

        val borderProgress = borderShimmer?.animationSpec?.let {
            getRatingShimmerProgress(borderShimmer.animationSpec)
        }

        ShimmerData(
            fillProgress = fillProgress,
            fillColors = fillShimmer?.colors,
            solidBorderOverFill = fillShimmer?.solidBorder ?: false,
            borderProgress = borderProgress,
            borderColors = borderShimmer?.colors
        )
    }
    return shimmerData
}

@Composable
private fun getRatingShimmerProgress(animationSpec: InfiniteRepeatableSpec<Float>): Float {

    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = animationSpec,
    )

    return progress
}

private fun getRatingFromTouchPosition(
    x: Float,
    itemIntervals: List<ClosedFloatingPointRange<Float>>,
    ratingBarDimension: Float,
    space: Float,
    totalCount: Int,
    ratingInterval: RatingInterval,
    allowZeroRating: Boolean
): Float {

    val ratingBarItemSize = (ratingBarDimension - space * (totalCount - 1)) / totalCount
    val ratingItemInterval = ratingBarItemSize + space

    var rating = 0f
    var isInInterval = false
    itemIntervals.forEachIndexed { index: Int, interval: ClosedFloatingPointRange<Float> ->
        if (interval.contains(x)) {
            rating = index.toFloat() + (x - interval.start) / ratingBarItemSize
            isInInterval = true
        }
    }

    rating = if (!isInInterval) (1 + x / ratingItemInterval).toInt().coerceAtMost(totalCount)
        .toFloat() else rating

    return rating
        .getRatingForInterval(ratingInterval = ratingInterval, allowZero = allowZeroRating)
        .coerceIn(0f, totalCount.toFloat())
}

private fun ratingItemPositions(
    itemSize: Float,
    space: Float,
    totalCount: Int
): List<ClosedFloatingPointRange<Float>> {

    val list = mutableListOf<ClosedFloatingPointRange<Float>>()

    for (i in 0 until totalCount) {
        val start = itemSize * i + space * i
        list.add(start..start + itemSize)
    }

    return list
}
