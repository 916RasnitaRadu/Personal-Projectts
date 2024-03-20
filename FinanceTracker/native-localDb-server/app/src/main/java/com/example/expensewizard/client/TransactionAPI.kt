package com.example.expensewizard.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object TransactionAPI {
    private const val BASE_URL = "http://172.21.224.1:80"

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val transactionService : TransactionService = retrofit.create(TransactionService::class.java)
}