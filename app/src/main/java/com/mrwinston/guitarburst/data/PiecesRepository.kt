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
    private var allPieces: LiveData<List<Piece>>

    init {
        val db: PiecesDatabase? = PiecesDatabase.getDatabase(application)
        piecesDao = db!!.PiecesDao()
        allPieces = piecesDao.getAllPieces()
    }

    fun getAllPieces(): LiveData<List<Piece>> {
        return allPieces
    }

    suspend fun getPieces(input: String): List<Piece> {
        return piecesDao.getPieces(input)
    }

}