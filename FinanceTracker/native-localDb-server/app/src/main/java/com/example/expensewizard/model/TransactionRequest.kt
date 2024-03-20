package com.example.expensewizard.model

import java.util.Date

data class TransactionRequest(
    var title: String,
    var type:Type,
    var category: String,
    var amount: Double,
    var timestamp: Date
)
