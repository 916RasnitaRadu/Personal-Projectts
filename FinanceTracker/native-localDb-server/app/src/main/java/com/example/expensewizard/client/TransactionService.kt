package com.example.expensewizard.client

import com.example.expensewizard.model.Transaction
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransactionService {
    @GET("/transactions")
    suspend fun getTransactions() : List<Transaction>

    @POST("/transactions")
    suspend fun postTransaction(@Body transaction: Transaction) : Response<Transaction>

    @DELETE("/transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Long) : Response<Transaction>

    @PUT("/transactions/{id}")
    suspend fun updateTransaction(@Path("id") id : Long, @Body transaction: Transaction) : Response<Transaction>

}