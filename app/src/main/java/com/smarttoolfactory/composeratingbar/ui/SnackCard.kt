package com.smarttoolfactory.composeratingbar.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.smarttoolfactory.composeratingbar.R
import com.smarttoolfactory.composeratingbar.model.Snack
import com.smarttoolfactory.composeratingbar.model.snacks
import com.smarttoolfactory.ratingbar.RatingBar
import kotlin.random.Random

@SuppressLint("ModifierParameter")
@Composable
fun SnackCard(
    modifier: Modifier = Modifier,
    snack: Snack,
    textColor: Color = remember(snack.id) { randomColor() },
    onRateChange: (Float) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            Image(
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = snack.imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = null
            )

            Column(Modifier.padding(8.dp)) {


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        color = textColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        text = snack.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    RatingBar(
                        rating = snack.rating,
                        painterEmpty = painterResource(id = R.drawable.star_background),
                        painterFilled = painterResource(id = R.drawable.star_foreground),
                        tintEmpty = Color(0xff795548),
                        tintFilled = Color(0xff795548),
                        animationEnabled = true,
                        itemSize = 22.dp
                    ) {
                        onRateChange(it)
                    }
                }



                Text(
                    fontSize = 14.sp,
                    text = "$${snack.price}"
                )

            }
        }
    }
}


@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_C)
@Composable
fun SnackCardPreview() {
    val snack = snacks.first()
    SnackCard(snack = snack, textColor = Color.Black) {}
}


private fun randomColor() = Color(
    Random.nextInt(255),
    Random.nextInt(255),
    Random.nextInt(255)
)