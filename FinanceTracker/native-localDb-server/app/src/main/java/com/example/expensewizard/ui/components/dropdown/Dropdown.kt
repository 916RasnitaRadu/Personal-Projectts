package com.example.expensewizard.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select an option") }
    val dropdownItems = listOf("Option 1", "Option 2", "Option 3", "Option 4")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            // Dropdown button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .border(1.dp, Color.Gray)
                    .background(Color(0xFFFFF8E1))
                    .padding(8.dp)
            ) {
                Row (horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = selectedOption)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }

            }

            // Dropdown menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, (-300).dp)
            ) {
                dropdownItems.forEach { item ->
                    DropdownMenuItem(text = {Text(item)},
                        onClick = {
                            selectedOption = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
