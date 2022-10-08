# Compose-RatingBar
🚀⭐️📊 Rating bar to set fixed value or change rating by gestures with custom png files, ImageVectors




https://user-images.githubusercontent.com/35650605/194719853-f000097b-de82-4d0a-a552-86fc77b16420.mp4

## Declrations

```kotlin
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
)
```

```kotlin
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
)
```

```kotlin
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
)
```

## Usage

```kotlin
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

    Slider(
        value = rating2,
        onValueChange = { rating2 = it },
        valueRange = 0f..5f
    )

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
```



