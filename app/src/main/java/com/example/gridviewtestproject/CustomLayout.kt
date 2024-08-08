package com.example.gridviewtestproject

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Constraints
import com.example.gridviewtestproject.ui.theme.MyItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomLazyGridView(
    modifier: Modifier = Modifier,
    data: List<MyItem>,
    content: @Composable (MyItem) -> Unit
) {
    val scrollState = rememberScrollState()
    LazyLayout(
        modifier = modifier
            .clipToBounds()
            .scrollable(
                scrollState,
                Orientation.Vertical,
                reverseDirection = true,
                overscrollEffect = ScrollableDefaults.overscrollEffect()
            )
            .background(Color.Yellow),
        itemProvider = {
            MyItemProvider(data, content)
        }

    ) { constraints: Constraints ->
        val place = data.indices.mapNotNull { index ->
            measure(index, constraints).onEach {
                Log.d("myTag##", "$index: height: ${it.height.toDp()}, width: ${it.width.toDp()}")
            }
        }
        var positionY = -scrollState.value
        val height = place.sumOf { it.first().height }
        layout(constraints.maxWidth, height) {
            place.forEach {
                it.forEach {
                    it.placeRelative(0, positionY)
                }
                positionY+= it.first().height

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
class MyItemProvider(
    private val data:List<MyItem>,
    private val content: @Composable (MyItem) -> Unit
): LazyLayoutItemProvider {
    override val itemCount: Int
        get() = data.size
    @Composable
    override fun Item(index: Int, key: Any) {
        content.invoke(data[index])
    }
}