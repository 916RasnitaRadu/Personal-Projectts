package com.example.expensewizard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.expensewizard.model.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsert(transaction: Transaction);

    @Delete
    suspend fun delete(transaction: Transaction);

    @Query("SELECT * FROM `Transaction`")
    fun getTransactions() : LiveData<List<Transaction>>

    @Query("SELECT * FROM `Transaction` WHERE id = :id")
    suspend fun getTransactionById(id : Long) : Transaction?

    @Upsert
    suspend fun insertOrUpdateTransactions(transactions: List<Transaction>)

    @androidx.room.Transaction
    suspend fun replaceAllTransactions(transactions: List<Transaction>) {
        deleteAllTransactions()
        insertOrUpdateTransactions(transactions)
    }

    @Query("DELETE FROM `Transaction`")
    suspend fun deleteAllTransactions()
}