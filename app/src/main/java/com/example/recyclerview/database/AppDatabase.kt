package com.example.recyclerview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.database.dao.ItemDao
import com.example.recyclerview.model.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ItemDao(): ItemDao
}