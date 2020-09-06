package com.example.recyclerview.api

import com.example.recyclerview.model.Item
import retrofit2.Call
import retrofit2.http.*

interface ItemService {

    @GET("itens")
    fun getAll(): Call<List<Item>>

    @GET("itens/{id}")
    fun get(@Path("id") id: Long): Call<Item>

    @Headers("Content-Type: application/json")
    @POST("itens")
    fun insert(@Body item: Item): Call<Item>

    @Headers("Content-Type: application/json")
    @PATCH("itens/{id}")
    fun update(@Path("id") id: Long, @Body item: Item): Call<Item>

    @DELETE("itens/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}