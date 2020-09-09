package com.mrwinston.guitarburst.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.mrwinston.guitarburst.data.model.Piece
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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

    suspend fun getByEra(era: String): List<Piece> {
        return piecesDao.getByEra(era)
    }

}