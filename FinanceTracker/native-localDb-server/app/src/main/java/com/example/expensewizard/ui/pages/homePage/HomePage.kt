package com.example.expensewizard.ui.pages.homePage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import com.example.expensewizard.ui.components.list.TransactionList
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.expensewizard.data.TransactionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(navController : NavController, transactionViewModel: TransactionViewModel) {
    val BG_COLOR = Color(0xFFB57EDC);
    val BG_COLOR_CARDS = Color(0xFFFFF8E1);
    val fontFamily: FontFamily = FontFamily.Default


    Box(modifier = Modifier
        .fillMaxSize()
        .background(BG_COLOR))
    {
        Column(
            Modifier
                .padding(20.dp)
                .fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(BG_COLOR_CARDS)
                .shadow(8.dp, shape = MaterialTheme.shapes.small)

            ) {
                Row (horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BG_COLOR_CARDS)
                        .padding(4.dp, 30.dp)){
                    Text(text = "Expense Wizardinho",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontFamily,
                    )
                }

            }
            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(580.dp)
                .background(BG_COLOR_CARDS)
                .padding(10.dp, 10.dp),
                contentAlignment = Alignment.Center,
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize())

                {

                    InfoComponent()
                    TransactionList(transactionViewModel,navController)
                    Button(colors = ButtonDefaults.buttonColors(Color(0xFFB89EFB)),
                        onClick = { navController.navigate("add") },
                        modifier = Modifier.padding(20.dp)
                            .size(width = 200.dp, height = 60.dp)

                    )
                    {
                        Text(text = "+ Another One",
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoComponent() {
    val BG_COLOR_CARDS = Color(0xFFFFF8E1);
    val fontFamily: FontFamily = FontFamily.Monospace
    Row (verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 3.dp)){

        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Cash",
                fontWeight = FontWeight(600),
                fontFamily = fontFamily,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
        }



        Text(
            text = "1.500$",
            fontWeight = FontWeight(600),
            fontFamily = fontFamily,
            fontSize = 18.sp,
        )
    }
}