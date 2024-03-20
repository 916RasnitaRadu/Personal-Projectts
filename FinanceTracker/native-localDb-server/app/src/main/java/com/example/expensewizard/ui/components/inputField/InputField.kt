package com.example.expensewizard.ui.components.inputField

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.time.format.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(text: TextFieldValue,
               onTextChanged: (TextFieldValue) -> Unit, placeHolder : String) {
    val BG_COLOR_CARDS = Color(0xFFFFF8E1);

    OutlinedTextField(
        value = text,
        onValueChange = {
            newText -> onTextChanged(newText)
        },
        modifier = Modifier
            .padding(2.dp)
            .height(80.dp)
            .background(BG_COLOR_CARDS), // Set the background color to transparent
        label = {
            Text(placeHolder, color = Color.Black)
        }
    )

}


