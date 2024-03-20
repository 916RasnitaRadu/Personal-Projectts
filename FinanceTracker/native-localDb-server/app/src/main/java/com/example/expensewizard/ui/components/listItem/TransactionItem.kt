package com.example.expensewizard.ui.components.listItem

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.model.Type
import com.example.expensewizard.ui.components.ClickableIcon.ClickableIcon
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionItem(transaction: Transaction,navController: NavController, onDelete: () -> Unit) {
    val BG_COLOR = Color(0xFFFFF8E1)
    val fontFamily: FontFamily = FontFamily.Default
    var dialogState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .size(300.dp, 120.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))
            .background(BG_COLOR)
            .shadow(2.dp, shape = RoundedCornerShape(2.dp), clip = false)
    )
    {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(BG_COLOR)
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Text(
                text = transaction.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = fontFamily
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(BG_COLOR)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .background(BG_COLOR)
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    Text(
                        text = transaction.category,
                        fontWeight = FontWeight(600),
                        fontFamily = fontFamily,
                        fontSize = 14.sp,

                    )
                    Text(
                        text = formatDate(transaction.timestamp),
                        fontWeight = FontWeight(600),
                        fontFamily = fontFamily,
                        fontSize = 14.sp,

                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .background(BG_COLOR)
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    if (transaction.type.equals("INCOME")) {
                        Text(
                            text = "+${transaction.amount}$",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily
                        )
                    }
                    else {
                        Text(
                            text = "-${transaction.amount}$",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily
                        )
                    }

                    Row(horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .background(BG_COLOR)
                            .fillMaxWidth()) {
                        ClickableIcon(icon = Icons.Default.Delete) {
                            dialogState = true
                        }
                        ClickableIcon(icon = Icons.Default.Edit) {
                            val transactionId = transaction.id
                            navController.navigate("update/$transactionId")
                        }

                    }

                }

            }


        }

        if (dialogState) {
            DeleteDialog(onYes = { onDelete() }, onNo = {dialogState=false})
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date : Date) : String {
    val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("MM/dd, HH:mm", Locale.ENGLISH)
    return formatter.format(zonedDateTime)
}

@Composable
fun DeleteDialog(onYes: () -> Unit, onNo: () -> Unit) {
    AlertDialog(onDismissRequest = { onNo() },
        title = { Text(text = "Delete Transaction")},
        text = { Text(text = "Are you sure you want to delete this transaction?")},
        confirmButton = {
            Button(onClick = { onYes()
                onNo()
                }
            ) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(onClick = { onNo() }) {
                Text(text = "Cancel")
            }
        }
        )
}