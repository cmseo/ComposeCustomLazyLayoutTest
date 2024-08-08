package com.example.gridviewtestproject

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.gridviewtestproject.ui.theme.GridViewTestProjectTheme
import com.example.gridviewtestproject.ui.theme.MyItem
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    private val myData = listOf(
        MyItem(
            spanCount = 2,
            height = 300.dp,
            text = "first"
        ),
        MyItem(
            spanCount = 1,
            height = 150.dp,
            text = "second"
        ),
        MyItem(
            spanCount = 3,
            height = 150.dp,
            text = "third"
        ),
        MyItem(
            spanCount = 1,
            height = 150.dp,
            text = "fourth"
        ),
        MyItem(
            spanCount = 1,
            height = 150.dp,
            text = "fifth"
        ),
        MyItem(
            spanCount = 1,
            height = 150.dp,
            text = "sixth"
        ),
        MyItem(
            spanCount = 1,
            height = 150.dp,
            text = "seventh"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GridViewTestProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(Modifier.padding(innerPadding)) {
                        item(key = "header") {
                            Text(text = "I am header !!")

                        }

                        item {
                            val totalSpan = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 4f else 8f
                            var size by remember { mutableStateOf(IntSize.Zero)}
                            Log.d("MainActivity", "totlspan: $totalSpan, size: ${size}")
                            CustomLazyGridView(
                                modifier = Modifier.onSizeChanged {
                                    size = it
                                },
                                data = myData
                            ) { data ->
                                val width = size.width * (data.spanCount.toFloat() / totalSpan)
                                Log.d("MainActivity", "spancount: ${data.spanCount}, width: ${width.roundToInt()}")
                                with (LocalDensity.current) {
                                    Box(
                                        modifier = Modifier
                                            .height(data.height)
                                            .width(
                                                width
                                                    .roundToInt()
                                                    .toDp()
                                            )
                                            .clip(RoundedCornerShape(24.dp))
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${data.text}\nspan: ${data.spanCount}\nheight: ${data.height}",
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        item(key = "footer") {
                            Text(text = "I am footer !!")
                        }
                    }
                }
            }
        }
    }
}

// LazyVerticalGrid 검토
// -> place.y를 컨트롤 할 수 없다.
@Preview
@Composable
fun GridView() {
    val item = (1..10).toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = object : Arrangement.Vertical {
            override fun Density.arrange(totalSize: Int, sizes: IntArray, outPositions: IntArray) {
                sizes.forEachIndexed { index, i ->
                    Log.d("grid", "$index] $i")
                    outPositions[index] = when(index) {
                        in (0..3) -> 0
                        in (4..6) -> 60
                        else -> 70
                    }
                }
            }
        }
    ) {
        items(item) {
            Box(modifier = Modifier
                .height(
                    (it * 10).dp
                )
                .border(
                    1.dp,
                    color = Color.Red
                )) {
                Text(text = it.toString())
            }
        }
    }
}