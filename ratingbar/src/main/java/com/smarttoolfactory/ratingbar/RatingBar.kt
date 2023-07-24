package com.smarttoolfactory.ratingbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.model.Shimmer
import com.smarttoolfactory.ratingbar.model.ShimmerData
import kotlinx.coroutines.launch

/**
 * Rating bar that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 *
 * @param rating value to be set on this rating bar
 * @param imageEmpty background for rating items. Item with borders to
 * show empty values
 * @param imageFilled foreground for rating items. Filled item to show percentage of rating
 * @param tintEmpty color for background and foreground items
 * @param itemSize size of the rating item to be displayed. This is intrinsic size of image
 * or vector file by default
 * @param animationEnabled when animation is enabled tap on any value is animated
 * @param gestureEnabled when gesture is not enabled value can only be changed by setting [rating]
 * @param shimmer shimmer effect for having a glow
 * @param itemCount maximum number of items
 * @param space space between rating items in dp
 * @param onRatingChange callback to notify user when rating has changed. This is helpful
 * for getting change after tap or drag gesture
 *
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageEmpty: ImageBitmap,
    imageFilled: ImageBitmap,
    tintEmpty: Color? = null,
    tintFilled: Color? = null,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    shimmer: Shimmer? = null,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {
    val intrinsicWidth = imageEmpty.width.toFloat()
    val intrinsicHeight = imageEmpty.height.toFloat()

    val colorFilterEmpty: ColorFilter? = remember(tintEmpty) {
        if (tintEmpty != null) {
            ColorFilter.tint(tintEmpty)
        } else null
    }

    val colorFilterFilled: ColorFilter? = remember(tintFilled) {
        if (tintFilled != null) {
            ColorFilter.tint(tintFilled)
        } else null
    }

    RatingBarImpl(
        modifier = modifier,
        rating = rating,
        intrinsicWidth = intrinsicWidth,
        intrinsicHeight = intrinsicHeight,
        itemSize = itemSize,
        animationEnabled = animationEnabled,
        gestureEnabled = gestureEnabled,
        shimmer = shimmer,
        itemCount = itemCount,
        space = space,
        block = { updatedRating: Float, spaceBetween: Float, shimmerData: ShimmerData? ->
            drawRatingImages(
                rating = updatedRating,
                itemCount = itemCount,
                imageEmpty = imageEmpty,
                imageFilled = imageFilled,
                colorFilterEmpty = colorFilterEmpty,
                colorFilterFilled = colorFilterFilled,
                shimmerData = shimmerData,
                space = spaceBetween,
            )
        },
        onRatingChange = onRatingChange
    )
}

/**
 * Rating bar that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 *
 * @param rating value to be set on this rating bar
 * @param painterEmpty background for rating items. Item with borders to
 * show empty values
 * @param painterFilled foreground for rating items. Filled item to show percentage of rating
 * @param tintEmpty color for background and foreground items
 * @param itemSize size of the rating item to be displayed. This is intrinsic size of image
 * or vector file by default
 * @param animationEnabled when animation is enabled tap on any value is animated
 * @param gestureEnabled when gesture is not enabled value can only be changed by setting [rating]
 * @param shimmer shimmer effect for having a glow
 * @param itemCount maximum number of items
 * @param space space between rating items in dp
 * @param onRatingChange callback to notify user when rating has changed. This is helpful
 * for getting change after tap or drag gesture
 *
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    painterEmpty: Painter,
    painterFilled: Painter,
    tintEmpty: Color? = DefaultColor,
    tintFilled: Color? = null,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    shimmer: Shimmer? = null,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {

    val painterWidth = painterEmpty.intrinsicSize.width
    val painterHeight = painterEmpty.intrinsicSize.height

    val colorFilterFilled: ColorFilter? = remember(tintFilled) {
        if (tintFilled != null) {
            ColorFilter.tint(tintFilled)
        } else null
    }

    RatingBarImpl(
        modifier = modifier,
        rating = rating,
        intrinsicWidth = painterWidth,
        intrinsicHeight = painterHeight,
        itemSize = itemSize,
        animationEnabled = animationEnabled,
        gestureEnabled = gestureEnabled,
        shimmer = shimmer,
        itemCount = itemCount,
        space = space,
        block = { updatedRating: Float, spaceBetween: Float, shimmerData: ShimmerData? ->
            drawRatingPainters(
                updatedRating,
                itemCount,
                painterEmpty,
                painterFilled,
                tintEmpty,
                colorFilterFilled,
                shimmerData,
                spaceBetween
            )
        },
        onRatingChange = onRatingChange
    )
}

/**
 * Rating bar that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 *
 * @param rating value to be set on this rating bar
 * @param imageVectorEmpty background for rating items. Item with borders to
 * show empty values
 * @param imageVectorFFilled foreground for rating items. Filled item to show percentage of rating
 * @param tintEmpty color for background and foreground items
 * @param itemSize size of the rating item to be displayed. This is intrinsic size of image
 * or vector file by default
 * @param animationEnabled when animation is enabled tap on any value is animated
 * @param gestureEnabled when gesture is not enabled value can only be changed by setting [rating]
 * @param shimmer shimmer effect for having a glow
 * @param itemCount maximum number of items
 * @param space space between rating items in dp
 * @param onRatingChange callback to notify user when rating has changed. This is helpful
 * for getting change after tap or drag gesture
 *
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageVectorEmpty: ImageVector,
    imageVectorFFilled: ImageVector,
    tintEmpty: Color? = DefaultColor,
    tintFilled: Color? = null,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    shimmer: Shimmer? = null,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {

    val painterBackground = rememberVectorPainter(image = imageVectorEmpty)
    val painterForeground = rememberVectorPainter(image = imageVectorFFilled)

    val painterWidth = painterBackground.intrinsicSize.width
    val painterHeight = painterBackground.intrinsicSize.height

    val colorFilterFilled: ColorFilter? = remember(tintFilled) {
        if (tintFilled != null) {
            ColorFilter.tint(tintFilled)
        } else null
    }

    RatingBarImpl(
        modifier = modifier,
        rating = rating,
        intrinsicWidth = painterWidth,
        intrinsicHeight = painterHeight,
        itemSize = itemSize,
        animationEnabled = animationEnabled,
        gestureEnabled = gestureEnabled,
        shimmer = shimmer,
        itemCount = itemCount,
        space = space,
        block = { updatedRating: Float, spaceBetween: Float, shimmerData: ShimmerData? ->
            drawRatingPainters(
                updatedRating,
                itemCount,
                painterBackground,
                painterForeground,
                tintEmpty,
                colorFilterFilled,
                shimmerData,
                spaceBetween
            )
        },
        onRatingChange = onRatingChange
    )
}

@Composable
private fun RatingBarImpl(
    modifier: Modifier = Modifier,
    rating: Float,
    itemSize: Dp = Dp.Unspecified,
    intrinsicWidth: Float,
    intrinsicHeight: Float,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    shimmer: Shimmer?,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    block: DrawScope.(
        rating: Float,
        space: Float,
        shimmerData: ShimmerData?,
    ) -> Unit,
    onRatingChange: ((Float) -> Unit)? = null
) {

    Box(modifier) {

        val height: Dp = if (itemSize != Dp.Unspecified) {
            itemSize
        } else {
            LocalDensity.current.run { intrinsicHeight.toDp() }
        }

        val spacePx: Float = LocalDensity.current.run { space.toPx() }

        val itemWidthPx: Float = if (itemSize != Dp.Unspecified) {
            LocalDensity.current.run { itemSize.toPx() }
        } else {
            intrinsicWidth
        }

        val totalWidth: Dp = LocalDensity.current.run {
            itemWidthPx.toDp() * itemCount + space * (itemCount - 1)
        }

        val itemIntervals = remember {
            ratingItemPositions(itemWidthPx, spacePx, itemCount)
        }

        val coerced = rating.coerceIn(0f, itemCount.toFloat())

        val coroutineScope = rememberCoroutineScope()
        val animatableRating = remember { Animatable(if (animationEnabled) 0f else coerced) }

        LaunchedEffect(key1 = if (gestureEnabled) Unit else coerced) {
            if (animationEnabled) {
                animatableRating.animateTo(
                    targetValue = coerced,
                    animationSpec = tween(300, easing = LinearEasing)

                )
            } else {
                animatableRating.snapTo(coerced)
            }
        }

        val gestureModifier = Modifier
            .pointerInput(Unit) {
                val ratingBarWidth = size.width.toFloat()
                detectHorizontalDragGestures { change, _ ->

                    val x = change.position.x
                    val newRating = getRatingFromTouchPosition(
                        x = x,
                        itemIntervals = itemIntervals,
                        ratingBarDimension = ratingBarWidth,
                        space = spacePx,
                        totalCount = itemCount,
                    )

                    coroutineScope.launch {
                        animatableRating.snapTo(newRating)
                        onRatingChange?.invoke(animatableRating.value)
                    }

                }
            }
            .pointerInput(Unit) {
                val ratingBarWidth = size.width.toFloat()

                detectTapGestures { change ->
                    val x = change.x
                    val newRating = getRatingFromTouchPosition(
                        x = x,
                        itemIntervals = itemIntervals,
                        ratingBarDimension = ratingBarWidth,
                        space = spacePx,
                        totalCount = itemCount,
                    )

                    coroutineScope.launch {
                        if (animationEnabled) {
                            animatableRating.animateTo(
                                targetValue = newRating,
                                animationSpec = tween(300, easing = LinearEasing)
                            )
                        } else {
                            animatableRating.snapTo(newRating)
                        }
                        onRatingChange?.invoke(animatableRating.value)
                    }
                }
            }


        if (shimmer != null) {
            val progress = getProgress(shimmer.animationSpec)
            Box(
                modifier = Modifier
                    .then(if (gestureEnabled) gestureModifier else Modifier)
                    .width(totalWidth)
                    .height(height)
                    .drawBehind {
                        block(
                            animatableRating.value,
                            spacePx,
                            ShimmerData(
                                colors = shimmer.colors,
                                progress = progress,
                                drawBorder = shimmer.drawBorder
                            )
                        )
                    }
            )
        } else {
            Box(
                modifier = Modifier
                    .then(if (gestureEnabled) gestureModifier else Modifier)
                    .width(totalWidth)
                    .height(height)
                    .drawBehind {
                        block(
                            animatableRating.value,
                            spacePx,
                            null
                        )
                    }
            )
        }
    }
}

@Composable
private fun getProgress(animationSpec: InfiniteRepeatableSpec<Float>): Float {

    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(

        initialValue = 0f,
        targetValue = 1f,
        animationSpec = animationSpec
    )

    return progress
}

private fun getRatingFromTouchPosition(
    x: Float,
    itemIntervals: List<ClosedFloatingPointRange<Float>>,
    ratingBarDimension: Float,
    space: Float,
    totalCount: Int
): Float {

    val ratingBarItemSize = (ratingBarDimension - space * (totalCount - 1)) / totalCount
    val ratingInterval = ratingBarItemSize + space

    var rating = 0f
    var isInInterval = false
    itemIntervals.forEachIndexed { index: Int, interval: ClosedFloatingPointRange<Float> ->
        if (interval.contains(x)) {
            rating = index.toFloat() + (x - interval.start) / ratingBarItemSize
            isInInterval = true
        }
    }

    rating = if (!isInInterval) (1 + x / ratingInterval).toInt().coerceAtMost(totalCount)
        .toFloat() else rating

    return rating.coerceIn(0f, totalCount.toFloat())
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

private fun DrawScope.drawRatingPainters(
    rating: Float,
    itemCount: Int,
    painterEmpty: Painter,
    painterFilled: Painter,
    tintEmpty: Color?,
    colorFilterFilled: ColorFilter?,
    shimmerData: ShimmerData?,
    space: Float
) {

    val imageWidth = size.height
    val ratingInt = rating.toInt()

    // End of rating bar
    val startOfEmptyItems = imageWidth * itemCount + space * (itemCount - 1)
    // Start of empty rating items
    val endOfFilledItems = rating * imageWidth + ratingInt * space
    // Rectangle width that covers empty items
    val rectWidth = startOfEmptyItems - endOfFilledItems

    drawWithLayer {

        // Draw foreground rating items
        for (i in 0 until itemCount) {
            val start = imageWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                with(painterFilled) {
                    draw(
                        size = Size(size.height, size.height),
                        colorFilter = colorFilterFilled
                    )
                }
            }
        }

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(endOfFilledItems, 0f),
            size = Size(rectWidth, height = size.height),
            blendMode = BlendMode.SrcIn
        )

        for (i in 0 until itemCount) {

            translate(left = (imageWidth * i + space * i), top = 0f) {
                with(painterEmpty) {
                    draw(
                        size = Size(size.height, size.height),
                        colorFilter = ColorFilter.tint(
                            tintEmpty ?: Color.Transparent,
                            blendMode = BlendMode.SrcIn
                        )
                    )
                }
            }
        }

        shimmerData?.let { shimmerData ->
            val progress = shimmerData.progress

            drawRect(
                brush = Brush.linearGradient(
                    shimmerData.colors,
                    start = Offset(
                        x = endOfFilledItems * progress - imageWidth,
                        y = endOfFilledItems * progress - imageWidth
                    ),
                    end = Offset(endOfFilledItems * progress, endOfFilledItems * progress)
                ),
                size = Size(endOfFilledItems, size.height),
                blendMode = BlendMode.SrcIn
            )

            if (shimmerData.drawBorder) {
                for (i in 0 until itemCount) {

                    translate(left = (imageWidth * i + space * i), top = 0f) {
                        with(painterEmpty) {
                            draw(
                                size = Size(size.height, size.height),
                                colorFilter = ColorFilter.tint(
                                    tintEmpty ?: Color.Transparent,
                                    blendMode = BlendMode.SrcIn
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawRatingImages(
    rating: Float,
    itemCount: Int,
    imageEmpty: ImageBitmap,
    imageFilled: ImageBitmap,
    colorFilterEmpty: ColorFilter?,
    colorFilterFilled: ColorFilter?,
    shimmerData: ShimmerData?,
    space: Float,
) {

    val imageWidth = size.height
    val ratingInt = rating.toInt()

    drawWithLayer {

        // Draw foreground rating items
        for (i in 0 until itemCount) {
            val start = imageWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                drawImage(
                    image = imageFilled,
                    dstSize = IntSize(size.height.toInt(), size.height.toInt()),
                    colorFilter = colorFilterFilled
                )
            }
        }

        // End of rating bar
        val startOfEmptyItems = imageWidth * itemCount + space * (itemCount - 1)
        // Start of empty rating items
        val endOfFilledItems = rating * imageWidth + ratingInt * space
        // Rectangle width that covers empty items
        val rectWidth = startOfEmptyItems - endOfFilledItems

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(endOfFilledItems, 0f),
            size = Size(rectWidth, height = size.height),
            blendMode = BlendMode.SrcIn
        )

        for (i in 0 until itemCount) {

            translate(left = (imageWidth * i + space * i), top = 0f) {
                drawImage(
                    image = imageEmpty,
                    dstSize = IntSize(size.height.toInt(), size.height.toInt()),
                    colorFilter = colorFilterEmpty
                )
            }
        }

        shimmerData?.let { shimmerData ->
            val progress = shimmerData.progress

            drawRect(
                brush = Brush.linearGradient(
                    shimmerData.colors,
                    start = Offset(
                        x = endOfFilledItems * progress - imageWidth,
                        y = endOfFilledItems * progress - imageWidth
                    ),
                    end = Offset(endOfFilledItems * progress, endOfFilledItems * progress)
                ),
                size = Size(endOfFilledItems, size.height),
                blendMode = BlendMode.SrcIn
            )

            if (shimmerData.drawBorder) {
                for (i in 0 until itemCount) {

                    translate(left = (imageWidth * i + space * i), top = 0f) {
                        drawImage(
                            image = imageEmpty,
                            dstSize = IntSize(size.height.toInt(), size.height.toInt()),
                            colorFilter = colorFilterEmpty
                        )
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

val DefaultColor = Color(0xffFFB300)
