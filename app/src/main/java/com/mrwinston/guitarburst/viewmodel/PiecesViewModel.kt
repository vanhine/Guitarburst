package com.mrwinston.guitarburst.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.common.flogger.FluentLogger
import com.mrwinston.guitarburst.data.PiecesRepository
import com.mrwinston.guitarburst.data.model.Piece
import kotlinx.coroutines.launch

class PiecesViewModel @ViewModelInject constructor(
    private val logger: FluentLogger,
    private val piecesRepository: PiecesRepository,
    application: Application): AndroidViewModel(application) {

    private val _piecesToDisplay = MutableLiveData<List<Piece>>()
    val piecesToDisplay: LiveData<List<Piece>> = _piecesToDisplay

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var checkedCategory = SearchCategory.TITLE

    var resultPiece: Piece? = null

    fun searchPieces(text: String) = viewModelScope.launch {
        _isLoading.value = true
        val pieces = when (checkedCategory) {
            SearchCategory.TITLE -> piecesRepository.getByTitle(text)
            SearchCategory.COMPOSER -> piecesRepository.getByComposer(text)
        }
        _piecesToDisplay.value = pieces
        _isLoading.value = false
    }

    enum class SearchCategory {
        TITLE, COMPOSER
    }
}