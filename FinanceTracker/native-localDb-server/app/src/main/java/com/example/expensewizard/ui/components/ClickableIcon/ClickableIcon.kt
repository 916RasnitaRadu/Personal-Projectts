package com.example.expensewizard.ui.components.ClickableIcon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(icon: ImageVector,
                  onClick: () -> Unit) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color.DarkGray,
        modifier = Modifier.padding(4.dp).clickable { onClick() }
    )
}
