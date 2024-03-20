package com.example.expensewizard.repository

import androidx.lifecycle.LiveData
import com.example.expensewizard.data.TransactionDao
import com.example.expensewizard.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TransactionRepository(private val dao : TransactionDao) {

    fun getTransactions() : LiveData<List<Transaction>> {
       return dao.getTransactions();
    }

    suspend fun upsert(transaction: Transaction) {
        dao.upsert(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        dao.delete(transaction);
    }

    suspend fun getTransactionById(id:Long ): Transaction? {
        return dao.getTransactionById(id)
    }

    suspend fun insertOrUpdateTransactions(transactions: List<Transaction>) {
        dao.insertOrUpdateTransactions(transactions)
    }

    suspend fun replaceAllTransactions(transactions: List<Transaction>) {
        dao.replaceAllTransactions(transactions)
    }
}