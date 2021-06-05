package com.mrwinston.guitarburst.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrwinston.guitarburst.data.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun FavoritesDao(): FavoritesDao
}