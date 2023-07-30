package com.smarttoolfactory.ratingbar

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
import androidx.compose.ui.unit.IntSize
import com.smarttoolfactory.ratingbar.model.ShimmerData

internal fun DrawScope.drawRatingPainters(
    rating: Float,
    itemCount: Int,
    painterEmpty: Painter,
    painterFilled: Painter,
    tintEmpty: Color?,
    colorFilterFilled: ColorFilter?,
    shimmerData: ShimmerData?,
    space: Float
) {

    // Width of single rating item
    val itemWidth = size.height
    // Height of single rating item
    val itemHeight = size.height

    val ratingInt = rating.toInt()

    // Width of rating bar with items and spaces combined
    val ratingBarWidth = itemWidth * itemCount + space * (itemCount - 1)
    // Height of the ratingbar
    val ratingBarHeight = size.height
    // Start of empty rating items
    val endOfFilledItems = rating * itemWidth + ratingInt * space
    // Rectangle width that covers empty items
    val rectWidth = ratingBarWidth - endOfFilledItems

    drawWithLayer {

        // Draw foreground rating items
        for (i in 0 until itemCount) {
            val start = itemWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                with(painterFilled) {
                    draw(
                        size = Size(itemWidth, itemHeight),
                        colorFilter = colorFilterFilled
                    )
                }
            }
        }

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(endOfFilledItems, 0f),
            size = Size(rectWidth, height = ratingBarHeight),
            blendMode = BlendMode.SrcIn
        )

        for (i in 0 until itemCount) {

            translate(left = (itemWidth * i + space * i), top = 0f) {
                with(painterEmpty) {
                    draw(
                        size = Size(itemWidth, itemHeight),
                        colorFilter = ColorFilter.tint(
                            tintEmpty ?: Color.Transparent,
                            blendMode = BlendMode.SrcIn
                        )
                    )
                }
            }
        }

        // Filled Shimmer Effect
        shimmerData?.run {

            if (fillProgress != null && !fillColors.isNullOrEmpty()){

                val progress = fillProgress

                drawRect(
                    brush = Brush.linearGradient(
                        colors = fillColors,
                        start = Offset(
                            x = endOfFilledItems * progress - itemWidth,
                            y = endOfFilledItems * progress - itemWidth
                        ),
                        end = Offset(endOfFilledItems * progress, endOfFilledItems * progress)
                    ),
                    size = Size(endOfFilledItems, ratingBarHeight),
                    blendMode = BlendMode.SrcIn
                )
            }


            if (solidBorderOverFill ) {
                for (i in 0 until itemCount) {

                    translate(left = (itemWidth * i + space * i), top = 0f) {
                        with(painterEmpty) {
                            draw(
                                size = Size(itemWidth, itemHeight),
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


    // Border Shimmer Effect
    shimmerData?.run {
        val progress = borderProgress

        if (progress != null && !borderColors.isNullOrEmpty()){
            drawWithLayer {

                for (i in 0 until itemCount) {

                    translate(left = (itemWidth * i + space * i), top = 0f) {
                        with(painterEmpty) {
                            draw(
                                size = Size(itemWidth, itemHeight),
                                colorFilter = ColorFilter.tint(
                                    tintEmpty ?: Color.Transparent,
                                    blendMode = BlendMode.SrcIn
                                )
                            )
                        }
                    }
                }

                drawRect(
                    brush = Brush.linearGradient(
                        borderColors,
                        start = Offset(
                            x = ratingBarWidth * progress - itemWidth,
                            y = ratingBarWidth * progress - itemWidth
                        ),
                        end = Offset(ratingBarWidth * progress, endOfFilledItems * progress)
                    ),
                    size = Size(ratingBarWidth, ratingBarHeight),
                    blendMode = BlendMode.SrcIn
                )
            }
        }
    }
}

internal fun DrawScope.drawRatingImages(
    rating: Float,
    itemCount: Int,
    imageEmpty: ImageBitmap,
    imageFilled: ImageBitmap,
    colorFilterEmpty: ColorFilter?,
    colorFilterFilled: ColorFilter?,
    shimmerData: ShimmerData?,
    space: Float,
) {

    // Width of single rating item
    val itemWidth = size.height
    // Height of single rating item
    val itemHeight = size.height

    val ratingInt = rating.toInt()

    // Width of rating bar with items and spaces combined
    val ratingBarWidth = itemWidth * itemCount + space * (itemCount - 1)
    // Height of the ratingbar
    val ratingBarHeight = size.height
    // Start of empty rating items
    val endOfFilledItems = rating * itemWidth + ratingInt * space
    // Rectangle width that covers empty items
    val rectWidth = ratingBarWidth - endOfFilledItems

    drawWithLayer {

        // Draw foreground rating items
        for (i in 0 until itemCount) {
            val start = itemWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                drawImage(
                    image = imageFilled,
                    dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                    colorFilter = colorFilterFilled
                )
            }
        }

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(endOfFilledItems, 0f),
            size = Size(rectWidth, height = ratingBarHeight),
            blendMode = BlendMode.SrcIn
        )

        for (i in 0 until itemCount) {

            translate(left = (itemWidth * i + space * i), top = 0f) {
                drawImage(
                    image = imageEmpty,
                    dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                    colorFilter = colorFilterEmpty
                )
            }
        }

        // Filled Shimmer Effect
        shimmerData?.run {

            if (fillProgress != null && !fillColors.isNullOrEmpty()){

                val progress = fillProgress

                drawRect(
                    brush = Brush.linearGradient(
                        colors = fillColors,
                        start = Offset(
                            x = endOfFilledItems * progress - itemWidth,
                            y = endOfFilledItems * progress - itemWidth
                        ),
                        end = Offset(endOfFilledItems * progress, endOfFilledItems * progress)
                    ),
                    size = Size(endOfFilledItems, ratingBarHeight),
                    blendMode = BlendMode.SrcIn
                )
            }


            if (solidBorderOverFill ) {
                for (i in 0 until itemCount) {

                    translate(left = (itemWidth * i + space * i), top = 0f) {
                        drawImage(
                            image = imageEmpty,
                            dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                            colorFilter = colorFilterEmpty
                        )
                    }
                }
            }
        }
    }


    // Border Shimmer Effect
    shimmerData?.run {
        val progress = borderProgress

        if (progress != null && !borderColors.isNullOrEmpty()){
            drawWithLayer {

                for (i in 0 until itemCount) {

                    translate(left = (itemWidth * i + space * i), top = 0f) {
                        drawImage(
                            image = imageEmpty,
                            dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                            colorFilter = colorFilterEmpty
                        )
                    }
                }

                drawRect(
                    brush = Brush.linearGradient(
                        borderColors,
                        start = Offset(
                            x = ratingBarWidth * progress - itemWidth,
                            y = ratingBarWidth * progress - itemWidth
                        ),
                        end = Offset(ratingBarWidth * progress, endOfFilledItems * progress)
                    ),
                    size = Size(ratingBarWidth, ratingBarHeight),
                    blendMode = BlendMode.SrcIn
                )
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
