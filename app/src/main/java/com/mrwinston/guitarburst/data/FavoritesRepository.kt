package com.mrwinston.guitarburst.data

import android.app.Application
import androidx.annotation.WorkerThread
import com.mrwinston.guitarburst.data.model.Favorite
import com.mrwinston.guitarburst.data.model.Piece
import javax.inject.Inject

class FavoritesRepository @Inject constructor(application: Application) {
    private var favoritesDao: FavoritesDao

    init {
        val db: FavoritesDatabase? = FavoritesDatabase.getDatabase(application)
        favoritesDao = db!!.FavoritesDao()
    }

    suspend fun getFavorites(): List<Favorite> {
        return favoritesDao.getFavorites()
    }

    suspend fun getFavorite(piece_uid: Int): Favorite? {
        return favoritesDao.getFavorite(piece_uid)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun setFavorite(piece: Piece) {
        if (getFavorite(piece.uid) == null) {
            favoritesDao.insert(Favorite(piece.uid))
        }
    }

    suspend fun removeFavorite(piece: Piece) {
        if (getFavorite(piece.uid) != null) {
            favoritesDao.remove(getFavorite(piece.uid)!!)
        }
    }
}