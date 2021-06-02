package com.mrwinston.guitarburst.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mrwinston.guitarburst.data.model.Piece

@Dao
interface PiecesDao {
    @Insert
    fun insert(piece: Piece)

    @Query("DELETE FROM pieces_table")
    fun deleteAllPieces()

    @Query("Select * from pieces_table where title like '%' || :input || '%' OR composer like '%' || :input || '%'")
    suspend fun getPieces(input: String): List<Piece>

    @Query("select * from pieces_table where title like '%' || :title || '%'")
    suspend fun getByTitle(title: String): List<Piece>

    @Query("select * from pieces_table where composer like '%' || :composer || '%'")
    suspend fun getByComposer(composer: String): List<Piece>

    @RawQuery
    suspend fun getByFilter(query: SimpleSQLiteQuery): List<Piece>
}