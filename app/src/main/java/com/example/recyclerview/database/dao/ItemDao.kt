package com.example.recyclerview.database.dao

import androidx.room.*
import com.example.recyclerview.model.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM itens ORDER BY id DESC")
    fun getAll(): List<Item>

    @Insert
    fun insert(item: Item): Long

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}