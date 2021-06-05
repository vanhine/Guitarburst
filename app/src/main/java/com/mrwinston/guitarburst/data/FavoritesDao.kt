package com.mrwinston.guitarburst.data

import androidx.room.Dao
import androidx.room.Insert
import com.mrwinston.guitarburst.data.model.Favorite

@Dao
interface FavoritesDao {
    @Insert
    fun insert(favorite: Favorite)
}