package com.amoustakos.rickandmorty.compose.animation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.theme.typography

@Composable
fun AnimatedCounter(
    modifier: Modifier = Modifier,
    count: Int,
    style: TextStyle
) {
    var oldCount by remember {
        mutableIntStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith  slideOutVertically { -it }
                },
                label = ""
            ) { charInner ->
                Text(
                    text = charInner.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAnimatedCounterDark() = AppTheme {
    Box(Modifier.size(100.dp).background(MaterialTheme.colorScheme.background)) {
        AnimatedCounter(
            modifier = Modifier.align(Alignment.Center),
            count = 25,
            style = typography.displayLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewAnimatedCounterLight() = AppTheme {
    Box(Modifier.size(100.dp).background(MaterialTheme.colorScheme.background)) {
        AnimatedCounter(
            modifier = Modifier.align(Alignment.Center),
            count = 25,
            style = typography.displayLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}