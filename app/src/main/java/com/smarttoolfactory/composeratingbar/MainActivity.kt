package com.smarttoolfactory.composeratingbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composeratingbar.ui.theme.ComposeRatingBarTheme
import com.smarttoolfactory.ratingbar.RatingBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRatingBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var rating by remember { mutableStateOf(3.7f) }

                    val image = ImageBitmap.imageResource(id = R.drawable.star)
                    val imageFull = ImageBitmap.imageResource(id = R.drawable.star_full)

                    Column {
                        RatingBar(
                            rating = rating,
                            spaceBetween = 10.dp,
                            imageBorder = image,
                            imageFull = imageFull
                        ) {
                            rating = it
                        }

                        Text("Rating: $rating")
                        RatingBar(
                            rating = 2.5f,
                            spaceBetween = 2.dp,
                            imageBorder = image,
                            imageFull = imageFull
                        )
                        RatingBar(
                            rating = 4.5f,
                            spaceBetween = 2.dp,
                            imageBorder = image,
                            imageFull = imageFull
                        )
                        RatingBar(
                            rating = 1.3f,
                            spaceBetween = 4.dp,
                            imageBorder = image,
                            imageFull = imageFull
                        )
                    }
                }
            }
        }
    }
}
