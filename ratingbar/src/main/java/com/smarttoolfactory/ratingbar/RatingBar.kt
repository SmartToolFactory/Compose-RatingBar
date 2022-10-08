package com.smarttoolfactory.ratingbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Rating bar that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageBackground: ImageBitmap,
    imageForeground: ImageBitmap,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {
    Box(modifier) {

        val height: Dp = if (itemSize != Dp.Unspecified) {
            itemSize
        } else {
            LocalDensity.current.run { imageBackground.height.toDp() }
        }

        val spacePx: Float = LocalDensity.current.run { space.toPx() }

        val itemWidthPx: Float = if (itemSize != Dp.Unspecified) {
            LocalDensity.current.run { itemSize.toPx() }
        } else {
            imageBackground.width.toFloat()
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
                detectDragGestures { change, _ ->

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

        Box(modifier = Modifier
            .then(if (gestureEnabled) gestureModifier else Modifier)
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRatingImages(
                    animatableRating.value,
                    itemCount,
                    imageBackground,
                    imageForeground,
                    spacePx
                )
            }
        )
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    painterBackground: Painter,
    painterForeground: Painter,
    tint: Color? = null,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {

    val painterWidth = painterBackground.intrinsicSize.width
    val vectorHeight = painterBackground.intrinsicSize.height

    val colorFilter: ColorFilter? = remember(tint) {
        if (tint != null) {
            ColorFilter.tint(tint)
        } else null
    }

    Box(modifier) {

        val height: Dp = if (itemSize != Dp.Unspecified) {
            itemSize
        } else {
            LocalDensity.current.run { vectorHeight.toDp() }
        }

        val spacePx: Float = LocalDensity.current.run { space.toPx() }

        val itemWidthPx: Float = if (itemSize != Dp.Unspecified) {
            LocalDensity.current.run { itemSize.toPx() }
        } else {
            painterWidth
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
                detectDragGestures { change, _ ->

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

        Box(modifier = Modifier
            .then(if (gestureEnabled) gestureModifier else Modifier)
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRatingPainters(
                    animatableRating.value,
                    itemCount,
                    painterBackground,
                    painterForeground,
                    colorFilter,
                    spacePx
                )
            }
        )
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageVectorBackground: ImageVector,
    imageVectorForeground: ImageVector,
    tint: Color = Color.Red,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {

    val painterBackground = rememberVectorPainter(image = imageVectorBackground)
    val painterForeground = rememberVectorPainter(image = imageVectorForeground)

    val painterWidth = painterBackground.intrinsicSize.width
    val vectorHeight = painterBackground.intrinsicSize.height

    val colorFilter = remember(tint) {
        ColorFilter.tint(tint)
    }

    Box(modifier) {

        val height: Dp = if (itemSize != Dp.Unspecified) {
            itemSize
        } else {
            LocalDensity.current.run { vectorHeight.toDp() }
        }

        val spacePx: Float = LocalDensity.current.run { space.toPx() }

        val itemWidthPx: Float = if (itemSize != Dp.Unspecified) {
            LocalDensity.current.run { itemSize.toPx() }
        } else {
            painterWidth
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
                detectDragGestures { change, _ ->

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

        Box(modifier = Modifier
            .then(if (gestureEnabled) gestureModifier else Modifier)
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRatingPainters(
                    animatableRating.value,
                    itemCount,
                    painterBackground,
                    painterForeground,
                    colorFilter,
                    spacePx
                )
            }
        )
    }
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
    painterBackground: Painter,
    painterForeground: Painter,
    colorFilter: ColorFilter?,
    space: Float
) {

    val imageWidth = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until itemCount) {

        val start = (imageWidth * i + space * i)

        translate(left = start, top = 0f) {
            with(painterBackground) {
                draw(
                    size = Size(size.height, size.height),
                    colorFilter = colorFilter
                )
            }
        }
    }

    drawWithLayer {
        for (i in 0 until itemCount) {
            val start = imageWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                with(painterForeground) {
                    draw(
                        size = Size(size.height, size.height),
                        colorFilter = colorFilter
                    )
                }
            }
        }

        val end = imageWidth * itemCount + space * (itemCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val rectWidth = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(rectWidth, height = size.height),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawRatingImages(
    rating: Float,
    itemCount: Int,
    imageBackground: ImageBitmap,
    imageForeground: ImageBitmap,
    space: Float
) {

    val imageWidth = size.height
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until itemCount) {

        val start = (imageWidth * i + space * i).toInt()

        drawImage(
            image = imageBackground,
            dstOffset = IntOffset(start, 0),
            dstSize = IntSize(size.height.toInt(), size.height.toInt())
        )
    }

    drawWithLayer {
        for (i in 0 until itemCount) {
            val start = (imageWidth * i + space * i).toInt()

            // Destination
            drawImage(
                image = imageForeground,
                dstOffset = IntOffset(start, 0),
                dstSize = IntSize(size.height.toInt(), size.height.toInt())
            )
        }

        val end = imageWidth * itemCount + space * (itemCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val rectWidth = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(rectWidth, height = size.height),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}
