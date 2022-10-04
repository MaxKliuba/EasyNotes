package com.android.maxclub.easynotes.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.android.maxclub.easynotes.R

@Composable
fun ColorPickerItem(
    color: Color,
    selected: Boolean,
    onSelect: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tintColor = Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), 0.7f))
    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                color = color,
                shape = CircleShape,
            )
            .clickable { onSelect(color) },
    ) {
        if (selected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_check_24),
                contentDescription = stringResource(R.string.selected_color_text),
                tint = tintColor,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}