package com.amoustakos.rickandmorty.compose.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import com.amoustakos.rickandmorty.compose.extensions.makeSpanStyle
import com.amoustakos.rickandmorty.compose.lists.models.AnnotatedTextAttribute
import com.amoustakos.rickandmorty.compose.lists.models.ClickableTextAttribute
import com.amoustakos.rickandmorty.compose.lists.models.TextAttribute
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.theme.black
import com.amoustakos.rickandmorty.compose.theme.typography
import kotlinx.collections.immutable.persistentListOf


@Composable
fun ClickableAnnotatedText(
    modifier: Modifier = Modifier,
    texts: AnnotatedTextAttribute
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            texts.texts.forEach { text ->
                when (text) {
                    is ClickableTextAttribute -> {
                        val link =
                            LinkAnnotation.Clickable(
                                text.tag,
                                TextLinkStyles(text.makeSpanStyle())
                            ) {
                                text.action?.invoke()
                            }
                        withLink(link) { append(text.text) }
                    }

                    else -> {
                        append(text.text)
                        addStyle(style = text.makeSpanStyle(), start = 0, end = text.text.length)
                    }
                }
            }
        }
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ClickableAnnotatedStringTextPreview() = AppTheme {
    ClickableAnnotatedText(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        texts = AnnotatedTextAttribute(
            persistentListOf(
                TextAttribute(
                    text = "See ",
                    style = typography.titleMedium.copy(color = black),
                    overflow = TextOverflow.Ellipsis
                ),
                ClickableTextAttribute(
                    tag = "terms",
                    text = "Terms and Conditions",
                    style = typography.titleMedium.copy(color = Color.Red, textDecoration = TextDecoration.Underline)
                ),
                TextAttribute(
                    text = " to continue",
                    style = typography.titleMedium.copy(color = black),
                    overflow = TextOverflow.Ellipsis
                )
            )
        )
    )
}
