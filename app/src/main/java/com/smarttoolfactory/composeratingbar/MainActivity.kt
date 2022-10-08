package com.smarttoolfactory.composeratingbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    var rating2 by remember { mutableStateOf(3.7f) }
                    var rating3 by remember { mutableStateOf(2.3f) }
                    var rating4 by remember { mutableStateOf(4.5f) }
                    var rating5 by remember { mutableStateOf(1.7f) }
                    var rating6 by remember { mutableStateOf(5f) }

                    val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
                    val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)

                    Column(modifier = Modifier.fillMaxSize()) {
                        RatingBar(
                            rating = rating,
                            space = 2.dp,
                            imageBackground = imageBackground,
                            imageForeground = imageForeground,
                            animationEnabled = false,
                            gestureEnabled = true,
                            itemSize = 60.dp
                        ) {
                            rating = it
                        }

                        Text(
                            "Rating: $rating",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        RatingBar(
                            rating = rating2,
                            painterBackground = painterResource(id = R.drawable.star_background),
                            painterForeground = painterResource(id = R.drawable.star_foreground),
                            tint = Color(0xff9C27B0),
                            animationEnabled = false,
                            gestureEnabled = false,
                            itemSize = 60.dp
                        ) {
                            rating2 = it
                        }

                        Slider(value = rating2, onValueChange = { rating2 = it }, valueRange = 0f..5f)

                        Text(
                            "Rating: $rating2",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        RatingBar(
                            rating = rating3,
                            painterBackground = painterResource(id = R.drawable.star_background),
                            painterForeground = painterResource(id = R.drawable.star_foreground),
                            tint = Color(0xff795548),
                            animationEnabled = true,
                            itemSize = 60.dp
                        ) {
                            rating3 = it
                        }

                        Text(
                            "Rating: $rating",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        RatingBar(
                            rating = rating4,
                            space = 2.dp,
                            imageVectorBackground = Icons.Default.FavoriteBorder,
                            imageVectorForeground = Icons.Default.Favorite,
                            tint = Color(0xffE91E63),
                            itemSize = 60.dp
                        ) {
                            rating4 = it
                        }

                        RatingBar(
                            rating = rating5,
                            space = 2.dp,
                            imageVectorBackground = ImageVector.vectorResource(id = R.drawable.outline_wb_cloudy_24),
                            imageVectorForeground = ImageVector.vectorResource(id = R.drawable.baseline_wb_cloudy_24),
                            tint = Color(0xff2196F3),
                            itemSize = 60.dp
                        ) {
                            rating5 = it
                        }

                        RatingBar(
                            rating = rating6,
                            imageVectorBackground = ImageVector.vectorResource(id = R.drawable.twotone_person_24),
                            imageVectorForeground = ImageVector.vectorResource(id = R.drawable.baseline_person_24),
                            tint = Color(0xff795548),
                            itemSize = 40.dp
                        ) {
                            rating6 = it
                        }

                        RatingBar(
                            rating = 4.5f,
                            space = 2.dp,
                            imageBackground = imageBackground,
                            imageForeground = imageForeground
                        )
                        RatingBar(
                            rating = 1.3f,
                            space = 4.dp,
                            imageBackground = imageBackground,
                            imageForeground = imageForeground
                        )
                    }
                }
            }
        }
    }
}
