package com.example.expensewizard.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensewizard.ToastHandler
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionDb : TransactionRepository,public val toastHandler: ToastHandler
) : ViewModel() {
    val transactions : LiveData<List<Transaction>> = transactionDb.getTransactions();

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                transactionDb.upsert(transaction)
            }
            catch (e: Exception) {
                toastHandler.showToast("Error adding transaction")
            }
        }
    }

    fun deleteTransaction(transactionId : Long) {
        viewModelScope.launch  (Dispatchers.IO) {
            val transaction : Transaction? = transactionDb.getTransactionById(transactionId)

            if (transaction != null) {
                try {
                    transactionDb.delete(transaction)
                } catch (e: Exception) {
                    toastHandler.showToast("Error deleting transaction")
                }
            }
        }
    }

     suspend fun getTransactionById(id : Long) : Transaction? {
        return transactionDb.getTransactionById(id)
     }

    fun insertOrUpdateTransactions(transactions: List<Transaction>) {
        viewModelScope.launch (Dispatchers.IO){
            transactionDb.insertOrUpdateTransactions(transactions)
        }
    }

    fun replaceAllTransactions(transactions: List<Transaction>) {
        viewModelScope.launch (Dispatchers.IO) {
            transactionDb.replaceAllTransactions(transactions)
        }
    }

}




class TransactionViewModelFactory (
    private val db: TransactionRepository,
    private val toast: ToastHandler
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(
            transactionDb = db,
            toastHandler = toast
        ) as T
    }
}
