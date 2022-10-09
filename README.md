# Compose-RatingBar

[![](https://jitpack.io/v/SmartToolFactory/Compose-RatingBar.svg)](https://jitpack.io/#SmartToolFactory/Compose-RatingBar)


Rating bar to set fixed value or change rating by gestures with custom png files, ImageVectors with
customization and shimmer effects.

https://user-images.githubusercontent.com/35650605/194745347-e91d2427-5d51-414c-9604-d0bd9b39f5f0.mp4

## Gradle Setup

To get a Git project into your build:

* Step 1. Add the JitPack repository to your build file Add it in your root build.gradle at the end
  of repositories:

```
allprojects {
  repositories {
      ...
      maven { url 'https://jitpack.io' }
  }
}
```

* Step 2. Add the dependency

```
dependencies {
	  implementation 'com.github.SmartToolFactory:Compose-RatingBar:Tag'
}
```

## Declrations

```kotlin
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageBackground: ImageBitmap,
    imageForeground: ImageBitmap,
    tint: Color? = null,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    shimmer: Shimmer? = null,
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
    shimmer: Shimmer? = null,
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
    imageVectorBackground: ImageVector,
    imageVectorForeground: ImageVector,
    tint: Color = DefaultColor,
    itemSize: Dp = Dp.Unspecified,
    animationEnabled: Boolean = true,
    gestureEnabled: Boolean = true,
    shimmer: Shimmer? = null,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    onRatingChange: ((Float) -> Unit)? = null
)
```

## Usage

```kotlin
@Composable
private fun RatingbarDemo() {
    Column(modifier = Modifier.fillMaxSize()) {
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

            val purple500 = Color(0xff9C27B0)

            RatingBar(
                rating = rating2,
                painterBackground = painterResource(id = R.drawable.star_background),
                painterForeground = painterResource(id = R.drawable.star_foreground),
                animationEnabled = false,
                gestureEnabled = false,
                tint = purple500,
                shimmer = Shimmer(
                    colors = listOf(
                        purple500.copy(.9f),
                        purple500.copy(.3f),
                        purple500.copy(.9f)
                    )
                ),
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

            val pink500 = Color(0xffE91E63)
            RatingBar(
                rating = rating4,
                space = 2.dp,
                imageVectorBackground = Icons.Default.FavoriteBorder,
                imageVectorForeground = Icons.Default.Favorite,
                shimmer = Shimmer(
                    color = pink500,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                ),
                tint = pink500,
                itemSize = 40.dp
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
                itemCount = 10,
                imageBackground = imageBackground,
                imageForeground = imageForeground,
                shimmer = Shimmer()
            )
            Spacer(modifier = Modifier.height(10.dp))

            RatingBar(
                rating = 8.3f,
                space = 4.dp,
                itemCount = 10,
                imageBackground = imageBackground,
                imageForeground = imageForeground,
                shimmer = Shimmer(
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 3000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            )
        }
    }
```
