package com.mrwinston.guitarburst.data

import android.app.Application
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
}