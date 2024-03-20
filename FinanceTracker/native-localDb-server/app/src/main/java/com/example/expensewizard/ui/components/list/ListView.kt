package com.example.expensewizard.ui.components.list

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.expensewizard.data.TransactionViewModel
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.ui.components.listItem.TransactionItem
import androidx.compose.runtime.LaunchedEffect
import com.example.expensewizard.client.TransactionAPI
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.viewModelScope
import com.example.expensewizard.client.deleteTransaction

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionList(viewModel : TransactionViewModel, navController: NavController) {
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }


    LaunchedEffect(true) {
        try {
            val response = TransactionAPI.transactionService.getTransactions()
            Log.i("TransactionList", "RESPONSE: $response")
            viewModel.replaceAllTransactions(response)

        } catch (e : Exception) {
            Log.e("TransactionList", "Error fetching transactions: ${e.message}", e)
        }
        finally {
            val observer = Observer<List<Transaction>> { updatedTransactions ->
                transactions = updatedTransactions
            }

            val liveData = viewModel.transactions
            liveData.observeForever(observer)
        }
    }



    LazyColumn (modifier = Modifier.height(400.dp)){
        items(transactions) {
                transaction -> TransactionItem(transaction = transaction, navController) {
                    deleteTransaction(transaction.id, viewModel = viewModel)
                }
                Spacer(modifier = Modifier.height(16.dp))

        }
    }



}


