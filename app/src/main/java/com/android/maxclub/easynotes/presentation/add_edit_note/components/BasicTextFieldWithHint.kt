package com.android.maxclub.easynotes.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.core.graphics.ColorUtils

@Composable
fun BasicTextFieldWithHint(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    backgroundColor: Color,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val textColor =
        Color(ColorUtils.blendARGB(backgroundColor.toArgb(), Color.Black.toArgb(), 0.7f))
    val hintColor =
        Color(ColorUtils.blendARGB(backgroundColor.toArgb(), Color.Black.toArgb(), 0.3f))

    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle.copy(color = textColor),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier.fillMaxWidth()
        )
        if (value.isEmpty()) {
            Text(
                text = hint,
                style = textStyle,
                color = hintColor,
            )
        }
    }
}