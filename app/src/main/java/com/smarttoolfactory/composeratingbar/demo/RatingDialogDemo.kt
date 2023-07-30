package com.smarttoolfactory.composeratingbar.demo

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smarttoolfactory.composeratingbar.R
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.FillShimmer
import com.smarttoolfactory.ratingbar.model.RatingInterval
import com.smarttoolfactory.ratingbar.model.ShimmerEffect

@Preview
@Composable
fun RatingDialogDemo() {

    var rating by remember {
        mutableStateOf(5f)
    }

    var showRatingDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                showRatingDialog = showRatingDialog.not()
            }
        ) {
            Text(text = "Show Rating Dialog")
        }

    }

    if (showRatingDialog) {
        RatingDialog(
            rating = rating,
            onRatingChange = {
                rating = it
            },
            onRateFinished = {
                rating = it
                showRatingDialog = false
                Toast.makeText(context, "User rated with $rating ⭐️", Toast.LENGTH_SHORT).show()
            },
            onDismissRequest = {
                showRatingDialog = false
            }
        )
    }
}

@Composable
fun RatingDialog(
    rating: Float,
    onRatingChange: (Float) -> Unit = {},
    onRateFinished: (Float) -> Unit = {},
    onDismissRequest: () -> Unit
) {

    val ratingState = rememberUpdatedState(newValue = rating)

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
        val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)

        Column(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.rate_us),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Do you love this App?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            RatingBar(
                rating = ratingState.value,
                space = 2.dp,
                imageEmpty = imageBackground,
                imageFilled = imageForeground,
                ratingInterval = RatingInterval.Half,
                shimmerEffect = ShimmerEffect(
                    FillShimmer(
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 3000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        solidBorder = true
                    )
                ),
            ) {
                onRatingChange(it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onRateFinished(ratingState.value)
                }
            ) {
                Text(text = "Rate")
            }
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = "Later")
            }
        }
    }
}

