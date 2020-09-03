package com.mrwinston.guitarburst.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mrwinston.guitarburst.data.PiecesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.Rule


class PiecesViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    @Test
    fun searchPieces_sets_isLoading() {
        val mockPiecesRepository: PiecesRepository = mock()
        val mockApplication: Application = mock()
        val viewModel = PiecesViewModel(mockPiecesRepository, mockApplication)
        viewModel.isLoading.observeForever {  }
    }
}