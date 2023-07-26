package com.smarttoolfactory.composeratingbar.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composeratingbar.model.Snack
import com.smarttoolfactory.composeratingbar.model.snacks
import com.smarttoolfactory.composeratingbar.ui.SnackCard

@Preview
@Composable
fun RatingBarListDemo() {
    val snackList = remember {
        mutableStateListOf<Snack>().apply {
            addAll(snacks)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {

        itemsIndexed(
            items = snackList,
            key = { _, snack: Snack ->
                snack.id
            }
        ) { index: Int, snack: Snack ->
            SnackCard(
                snack = snack,
                onRateChange = { newRating ->
                    snackList[index] = snackList[index].copy(rating = newRating)
                }
            )
        }

    }
}