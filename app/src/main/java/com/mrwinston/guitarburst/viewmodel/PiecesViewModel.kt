package com.mrwinston.guitarburst.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrwinston.guitarburst.data.FavoritesRepository
import com.mrwinston.guitarburst.data.PiecesRepository
import com.mrwinston.guitarburst.data.model.Favorite
import com.mrwinston.guitarburst.data.model.Piece
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PiecesViewModel @Inject constructor(
    private val piecesRepository: PiecesRepository,
    private val favoritesRepository: FavoritesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _piecesToDisplay = MutableLiveData<List<Piece>>()
    val piecesToDisplay: LiveData<List<Piece>> = _piecesToDisplay

    private val _favoritesToDisplay = MutableLiveData<List<Piece>>()
    val favoritesToDisplay: LiveData<List<Piece>> = _favoritesToDisplay

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var checkedCategory = SearchCategory.TITLE

    var resultPiece: Piece? = null

    fun populateFavorites() = viewModelScope.launch {
        _isLoading.value = true
        val favorites = favoritesRepository.getFavorites()
        val favoritePieces = mutableListOf<Piece>()
        for (favorite in favorites) {
            favoritePieces.add(piecesRepository.getByUid(favorite.fave_uid))
        }
        _favoritesToDisplay.value = favoritePieces
        _isLoading.value = false

    }

    fun searchPieces(text: String) = viewModelScope.launch {
        _isLoading.value = true
        val pieces = when (checkedCategory) {
            SearchCategory.TITLE -> piecesRepository.getByTitle(text)
            SearchCategory.COMPOSER -> piecesRepository.getByComposer(text)
        }
        _piecesToDisplay.value = pieces
        _isLoading.value = false
    }

    fun filterPieces(filter: PiecesFilter) = viewModelScope.launch {
        _isLoading.value = true
        val pieces = piecesRepository.getByFilter(
            filter.era,
            filter.length,
            filter.minDifficulty,
            filter.maxDifficulty
        )
        _piecesToDisplay.value = pieces
        _isLoading.value = false
    }

    fun setFavoritePiece(fav_piece: Piece) = CoroutineScope(Dispatchers.IO).launch {
        favoritesRepository.setFavorite(fav_piece)
    }

    fun removeFavoritePiece(piece: Piece) = viewModelScope.launch {
        favoritesRepository.removeFavorite(piece)
    }

    fun isFavorite(piece: Piece): LiveData<Boolean> {
        var favorites: List<Favorite>
        var favPiece: Favorite?
        val result: MutableLiveData<Boolean> = MutableLiveData(false)
        viewModelScope.launch {
            favorites = favoritesRepository.getFavorites()
            favPiece = favoritesRepository.getFavorite(piece.uid)
            result.postValue(favorites.contains(favPiece))
        }
        return result
    }

    data class PiecesFilter(
        val era: String,
        val length: String,
        val minDifficulty: Int,
        val maxDifficulty: Int
    )

    enum class SearchCategory {
        TITLE, COMPOSER
    }
}