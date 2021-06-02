package com.mrwinston.guitarburst.data

import android.app.Application
import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mrwinston.guitarburst.data.model.Piece
import javax.inject.Inject

class PiecesRepository @Inject constructor(application: Application) {
    private var piecesDao: PiecesDao

    init {
        val db: PiecesDatabase? = PiecesDatabase.getDatabase(application)
        piecesDao = db!!.PiecesDao()
    }

    suspend fun getPieces(input: String): List<Piece> {
        return piecesDao.getPieces(input)
    }

    suspend fun getByTitle(title: String): List<Piece> {
        return piecesDao.getByTitle(title)
    }

    suspend fun getByComposer(composer: String): List<Piece> {
        return piecesDao.getByComposer(composer)
    }

    suspend fun getByFilter(
        era: String,
        length: String,
        minDifficulty: Int,
        maxDifficulty: Int
    ): List<Piece> {
        var queryString =
            "select * from pieces_table where difficulty between $minDifficulty and $maxDifficulty"
        if (!era.equals("Any", true)) {
            queryString += " and era like '%$era%\'"
        }
        if (!length.equals("Any", true)) {
            queryString += " and duration like '%$length%'"
        }
        Log.d("Repository", "Sending Query: $queryString")
        val query = SimpleSQLiteQuery("$queryString;")
        return piecesDao.getByFilter(query)
    }
}