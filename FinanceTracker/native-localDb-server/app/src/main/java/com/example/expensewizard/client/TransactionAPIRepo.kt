package com.example.expensewizard.client

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.expensewizard.MainActivity
import com.example.expensewizard.data.TransactionViewModel
import com.example.expensewizard.model.Transaction
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

@OptIn(DelicateCoroutinesApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun postTransaction(transaction: Transaction, viewModel: TransactionViewModel) {
    GlobalScope.launch(Dispatchers.IO) {
        val response = try {
            Log.i("POST", "Trying to post transaction")
            viewModel.addTransaction(transaction)
            TransactionAPI.transactionService.postTransaction(transaction)

        } catch (e: HttpException) {
            Log.e("POST", "HTTP error ${e.message}")

        } catch (e : IOException) {
            Log.e("POST", "IO error ${e.message}")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun putTransaction(transaction: Transaction, viewModel: TransactionViewModel) {
    GlobalScope.launch(Dispatchers.IO) {
        val response = try {
            Log.i("PUT", "Trying to put/update transaction")
            TransactionAPI.transactionService.updateTransaction(transaction.id,transaction)
        } catch (e: HttpException) {
            Log.e("PUT", "HTTP error ${e.message}")
            viewModel.toastHandler.showToast("Error at updating transaction")
        } catch (e : IOException) {
            Log.e("PUT", "IO error ${e.message}")
            viewModel.toastHandler.showToast("Error at updating transaction")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun deleteTransaction(transactionId: Long, viewModel: TransactionViewModel) {
    GlobalScope.launch(Dispatchers.IO) {
        val response = try {
            Log.i("DELETE", "Trying to delete transaction")

            viewModel.deleteTransaction(transactionId)
            TransactionAPI.transactionService.deleteTransaction(transactionId)
        } catch (e: HttpException) {
            Log.e("DELETE", "HTTP error ${e.message}")
        } catch (e : IOException) {
            Log.e("DELETE", "IO error ${e.message}")
   //         viewModel.toastHandler.showToast("Error at deleting transaction")
        }
    }
}
