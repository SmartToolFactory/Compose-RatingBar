package com.smarttoolfactory.ratingbar

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Rating bar that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageBorder: ImageBitmap,
    imageFull: ImageBitmap,
    itemCount: Int = 5,
    spaceBetween: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
) {

    val height = LocalDensity.current.run { imageBorder.height.toDp() }
    val width = LocalDensity.current.run { imageBorder.width.toDp() }
    val space = LocalDensity.current.run { spaceBetween.toPx() }
    val totalWidth = width * itemCount + spaceBetween * (itemCount - 1)

    val itemIntervals = remember {
        ratingItemEndPositions(imageBorder.width.toFloat(), space, itemCount)
    }

    var ratingUpdated by remember {
        mutableStateOf(rating)
    }

    Box(
        modifier
            .border(2.dp, Color.Green)
            .pointerInput(Unit) {
                val ratingBarWidth = size.width.toFloat()
                detectDragGestures { change, _ ->

                    val x = change.position.x
                    ratingUpdated = getRatingFromTouchPosition(
                        x = x,
                        itemIntervals = itemIntervals,
                        ratingBarDimension = ratingBarWidth,
                        space = space,
                        totalCount = itemCount,
                    )

                    onRatingChange?.invoke(ratingUpdated)
                }
            }
            .pointerInput(Unit) {
                val ratingBarWidth = size.width.toFloat()

                detectTapGestures { change ->
                    val x = change.x
                    ratingUpdated = getRatingFromTouchPosition(
                        x = x,
                        itemIntervals = itemIntervals,
                        ratingBarDimension = ratingBarWidth,
                        space = space,
                        totalCount = itemCount,
                    )

                    onRatingChange?.invoke(ratingUpdated)
                }
            }
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRating(ratingUpdated, itemCount, imageBorder, imageFull, space)
            })
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

    return rating
}

private fun ratingItemEndPositions(
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

private fun DrawScope.drawRating(
    rating: Float,
    itemCount:Int,
    image: ImageBitmap,
    imageFull: ImageBitmap,
    space: Float
) {

    val imageWidth = image.width.toFloat()
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until itemCount) {

        val start = imageWidth * i + space * i

        drawImage(
            image = image,
            topLeft = Offset(start, 0f)
        )
    }

    drawWithLayer {
        for (i in 0 until itemCount) {
            val start = imageWidth * i + space * i
            // Destination
            drawImage(
                image = imageFull,
                topLeft = Offset(start, 0f)
            )
        }

        val end = imageWidth * itemCount + space * (itemCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val size = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
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
