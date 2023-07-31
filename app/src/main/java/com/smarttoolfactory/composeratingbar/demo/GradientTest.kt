package com.smarttoolfactory.composeratingbar.demo

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composeratingbar.R


private val gradientColors = listOf(
    Color.Red,
    Color.Blue,
    Color.Green
)

@Preview
@Composable
private fun GradientTest() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        GradientWithSliderTest()
        GradientWithInfiniteTransitionTest()
    }
}

@Preview
@Composable
private fun GradientWithSliderTest() {
    Column(Modifier.fillMaxWidth()) {

        val painterStar = painterResource(id = R.drawable.star_foreground)

        var progress by remember {
            mutableStateOf(0f)
        }

        val density = LocalDensity.current
        val widthDp = density.run { 1000f.toDp() }

        // RECTANGLE 1
        Box(
            modifier = Modifier
                .width(width = widthDp)
                .height(widthDp / 5)
                .drawWithCache {

                    val widthPx = size.width
                    val heightPx = size.height
                    val brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(
                            widthPx * progress,
                            0f
                        ),
                        end = Offset(
                            widthPx * progress + widthPx,
                            heightPx
                        )

                    )

                    onDrawBehind {
                        drawRect(
                            brush = brush
                        )
                    }
                }
        )

        // STARS 1
        Box(
            modifier = Modifier
                .width(width = widthDp)
                .height(widthDp / 5f)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {

                    val widthPx = size.width
                    val heightPx = size.height

                    val itemWidth = widthPx / 5f

                    val brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(
                            widthPx * progress,
                            0f
                        ),
                        end = Offset(
                            widthPx * progress + widthPx,
                            heightPx
                        )

                    )

                    onDrawBehind {

                        // Destination
                        for (i in 0 until 5) {

                            translate(left = (itemWidth * i), top = 0f) {
                                with(painterStar) {
                                    draw(
                                        size = Size(itemWidth, itemWidth)
                                    )
                                }
                            }
                        }

                        // Source
                        drawRect(
                            brush = brush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // RECT 2
        Box(
            modifier = Modifier
                .width(width = widthDp)
                .height(widthDp / 5)
                .drawWithCache {
                    val widthPx = size.width

                    val itemWidth = widthPx / 5f

                    val brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(
                            widthPx * progress - itemWidth,
                            widthPx * progress - itemWidth,
                        ),
                        end = Offset(
                            widthPx * progress,
                            widthPx * progress,
                        )

                    )

                    onDrawBehind {
                        drawRect(
                            brush = brush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )

        // STARS 2
        Box(
            modifier = Modifier
                .width(width = widthDp)
                .height(widthDp / 5)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val widthPx = size.width

                    val itemWidth = widthPx / 5f

                    val brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(
                            widthPx * progress- itemWidth,
                            widthPx * progress - itemWidth,
                        ),
                        end = Offset(
                            widthPx * progress,
                            widthPx * progress,
                        )

                    )

                    onDrawBehind {
                        // Destination
                        for (i in 0 until 5) {

                            translate(left = (itemWidth * i), top = 0f) {
                                with(painterStar) {
                                    draw(
                                        size = Size(itemWidth, itemWidth)
                                    )
                                }
                            }
                        }

                        // Source
                        drawRect(
                            brush = brush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )


        Text("Progress: $progress")
        Slider(
            value = progress,
            onValueChange = { progress = it },
            valueRange = 0f..1f
        )

    }
}

@Preview
@Composable
private fun GradientWithInfiniteTransitionTest() {
    Column(Modifier.fillMaxWidth()) {

        val painterStar = painterResource(id = R.drawable.star_foreground)
        val transition = rememberInfiniteTransition(label = "shimmer")
        val progress by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5000,
                )
            ), label = "shimmer"
        )

        val density = LocalDensity.current
        val widthDp = density.run { 1000f.toDp() }

        Box(
            modifier = Modifier
                .width(width = widthDp)
                .height(widthDp / 5)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {

                    val widthPx = size.width
                    val heightPx = size.height

                    val itemWidth = widthPx / 5f

                    val brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(
                            widthPx * progress,
                            0f
                        ),
                        end = Offset(
                            widthPx * progress + widthPx,
                            heightPx
                        )

                    )

                    onDrawBehind {

                        // Destination
                        for (i in 0 until 5) {

                            translate(left = (itemWidth * i), top = 0f) {
                                with(painterStar) {
                                    draw(
                                        size = Size(itemWidth, itemWidth)
                                    )
                                }
                            }
                        }

                        // Source
                        drawRect(
                            brush = brush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .width(width = widthDp)
                .height(widthDp / 5)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val widthPx = size.width

                    val itemWidth = widthPx / 5f

                    val brush = Brush.linearGradient(
                        colors = gradientColors,

                        start = Offset(
                            widthPx * progress - itemWidth,
                            widthPx * progress - itemWidth,
                        ),
                        end = Offset(
                            widthPx * progress,
                            widthPx * progress,
                        )

                    )

                    onDrawBehind {
                        // Destination
                        for (i in 0 until 5) {

                            translate(left = (itemWidth * i), top = 0f) {
                                with(painterStar) {
                                    draw(
                                        size = Size(itemWidth, itemWidth)
                                    )
                                }
                            }
                        }

                        // Source
                        drawRect(
                            brush = brush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )
    }
}