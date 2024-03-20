package com.example.expensewizard

import android.app.Application
import androidx.room.Room
import com.example.expensewizard.data.TransactionDao
import com.example.expensewizard.data.TransactionDatabase

class App : Application() {
    private var db: TransactionDatabase? = null

    init {
        instance = this
    }

    private fun getDb(): TransactionDatabase {
        if (db != null) {
            return db!!
        } else {

            db = Room.databaseBuilder(
                instance.applicationContext,
                TransactionDatabase::class.java,
                "TransactionDatabase"
            ).fallbackToDestructiveMigration().build()
            return db!!
        }
    }


    companion object {
        private lateinit var instance: App

        fun getDao(): TransactionDao {
            return instance.getDb().appDao()
        }
    }
}
