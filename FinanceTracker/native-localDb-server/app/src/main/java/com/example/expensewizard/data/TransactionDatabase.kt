package com.example.expensewizard.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.utils.DateConverter

@Database(
    entities = [Transaction::class],
    version = 6
)
@TypeConverters(DateConverter::class)
public abstract class TransactionDatabase : RoomDatabase() {

    abstract fun appDao() : TransactionDao;
}