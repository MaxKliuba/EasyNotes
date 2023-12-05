package com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.android.maxclub.easynotes.R

@Composable
fun NoteComponent(
    title: TextFieldValue,
    onChangeTitle: (TextFieldValue) -> Unit,
    content: TextFieldValue,
    onChangeContent: (TextFieldValue) -> Unit,
    noteColor: Color,
    modifier: Modifier = Modifier,
) {
    val cornerRadius = 16.dp
    val cutCornerSize = 32.dp

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = noteColor,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(
                            noteColor.toArgb(),
                            Color.Black.toArgb(),
                            0.2f
                        )
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            BasicTextFieldWithHint(
                value = title,
                onValueChange = onChangeTitle,
                hint = stringResource(R.string.title_hint_text),
                backgroundColor = noteColor,
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            BasicTextFieldWithHint(
                value = content,
                onValueChange = onChangeContent,
                hint = stringResource(R.string.content_hint_text),
                backgroundColor = noteColor,
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            )
        }
    }
}