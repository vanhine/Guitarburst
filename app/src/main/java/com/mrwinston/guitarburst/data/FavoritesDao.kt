package com.mrwinston.guitarburst.data

import androidx.room.*
import com.mrwinston.guitarburst.data.model.Favorite

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(favorite: Favorite)

    @Query("Select * from favorites_table")
    suspend fun getFavorites(): List<Favorite>

    @Query("Select * from favorites_table where fave_uid=:piece_uid")
    suspend fun getFavorite(piece_uid: Int): Favorite?

    @Delete
    suspend fun remove(favorite: Favorite)
}