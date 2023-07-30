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
                    shimmerData.fillColors,
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

        shimmerData?.let { shimmerData ->
            val progress = shimmerData.progress

            drawRect(
                brush = Brush.linearGradient(
                    shimmerData.fillColors,
                    start = Offset(
                        x = endOfFilledItems * progress - itemWidth,
                        y = endOfFilledItems * progress - itemWidth
                    ),
                    end = Offset(endOfFilledItems * progress, endOfFilledItems * progress)
                ),
                size = Size(endOfFilledItems, ratingBarHeight),
                blendMode = BlendMode.SrcIn
            )

            if (shimmerData.drawBorder && shimmerData.borderColors.isNullOrEmpty()) {
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

    drawWithLayer {

        shimmerData?.let {

            val progress = shimmerData.progress

            if (shimmerData.drawBorder) {
                for (i in 0 until itemCount) {

                    translate(left = (itemWidth * i + space * i), top = 0f) {
                        drawImage(
                            image = imageEmpty,
                            dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                            colorFilter = colorFilterEmpty
                        )
                    }
                }

                shimmerData.borderColors?.let {
                    drawRect(
                        brush = Brush.linearGradient(
                            shimmerData.borderColors,
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
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}
