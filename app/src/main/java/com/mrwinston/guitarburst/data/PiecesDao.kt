package com.mrwinston.guitarburst.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mrwinston.guitarburst.data.model.Piece

@Dao
interface PiecesDao {
    @Insert
    fun insert(piece: Piece)

    @Query("DELETE FROM pieces_table")
    fun deleteAllPieces()

    @Query("Select * from pieces_table where title like '%' || :input || '%' OR composer like '%' || :input || '%'")
    suspend fun getPieces(input: String): List<Piece>
}