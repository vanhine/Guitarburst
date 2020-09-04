package com.mrwinston.guitarburst.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mrwinston.guitarburst.data.PiecesRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executors

@kotlinx.coroutines.ExperimentalCoroutinesApi
class PiecesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun searchPieces_sets_isLoading() = testCoroutineScope.runBlockingTest {
        val mockPiecesRepository: PiecesRepository = mock()
        val mockApplication: Application = mock()
        val viewModel = PiecesViewModel(mockPiecesRepository, mockApplication)
        viewModel.isLoading.observeForever { }
        viewModel.searchPieces("Adam")
        assertThat(viewModel.isLoading.value).isFalse()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}