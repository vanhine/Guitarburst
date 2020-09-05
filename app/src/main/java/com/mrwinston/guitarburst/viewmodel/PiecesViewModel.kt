package com.mrwinston.guitarburst.viewmodel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrwinston.guitarburst.data.PiecesRepository
import com.mrwinston.guitarburst.data.model.Piece
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PiecesViewModel @ViewModelInject constructor(
    private val piecesRepository: PiecesRepository,
    application: Application): AndroidViewModel(application) {

    private val _piecesToDisplay = MutableLiveData<List<Piece>>()
    val piecesToDisplay: LiveData<List<Piece>> = _piecesToDisplay

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchPieces(text: String) = viewModelScope.launch {
        _isLoading.value = true
        val pieces = piecesRepository.getPieces(text)
        _piecesToDisplay.value = pieces
        _isLoading.value = false
        //Log.d("ViewModel", "Just got ${pieces.size} pieces")
    }
}