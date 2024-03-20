package com.example.expensewizard.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity
data class Transaction(
   @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var type:String,
    var category: String,
    var amount: Double,
    var timestamp: Date,
) {
//    companion object {
//        var currentId = 1
//    }
//
//    constructor(title: String, type: Type, category: String, amount: Double, timestamp: Date) :
//            this(currentId++,title,type,category,amount,timestamp)
//    {
//        Log.i("Model Transaction Class: ", "Current id is $currentId") }


}