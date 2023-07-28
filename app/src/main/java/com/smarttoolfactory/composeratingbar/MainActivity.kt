@file:OptIn(ExperimentalFoundationApi::class)

package com.smarttoolfactory.composeratingbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.composeratingbar.demo.RatingBarListDemo
import com.smarttoolfactory.composeratingbar.demo.RatingDialogDemo
import com.smarttoolfactory.composeratingbar.demo.RatingbarDemo
import com.smarttoolfactory.composeratingbar.ui.theme.ComposeRatingBarTheme
import kotlinx.coroutines.launch

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
                    Column(modifier = Modifier.fillMaxSize()) {
                        HomeContent()
                    }
                }
            }
        }
    }
}


@Composable
private fun HomeContent() {

    val pagerState: PagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    TabRow(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage
    ) {
        // Add tabs for all of our pages
        tabList.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }

    HorizontalPager(
        modifier = Modifier.padding(8.dp),
        state = pagerState,
        pageCount = tabList.size
    ) { page: Int ->

        when (page) {
            0 -> RatingbarDemo()
            1 -> RatingBarListDemo()
            else -> RatingDialogDemo()
        }
    }
}

internal val tabList =
    listOf(
        "Properties",
        "List Items",
        "Rating Dialog"
    )

