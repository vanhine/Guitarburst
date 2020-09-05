package com.mrwinston.guitarburst.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.google.common.truth.Truth.assertThat
import com.mrwinston.guitarburst.data.PiecesRepository
import com.mrwinston.guitarburst.data.model.Piece
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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
    fun searchPieces_setsPiecesToDisplay() = testCoroutineScope.runBlockingTest {
        val mockPiecesRepository: PiecesRepository = mock()
        val mockApplication: Application = mock()
        val viewModel = PiecesViewModel(mockPiecesRepository, mockApplication)
        val resultPiece1 = Piece(
            0,
            "Fake Title",
            "Adam",
            10,
            9,
            "XI",
            "medium",
            "modern",
            "NoName",
            "NoName",
            "Unit Tests Rule"
        )
        val resultPiece2 = Piece(
            1,
            "Fakest Title",
            "Taylor",
            11,
            10,
            "I",
            "long",
            "modern",
            "NoName",
            "NoName",
            "Unit Tests Rule"
        )
        val results = listOf(resultPiece1, resultPiece2)
        viewModel.viewModelScope.launch {
            doReturn(results).whenever(mockPiecesRepository).getPieces(any())
        }
        viewModel.piecesToDisplay.observeForever {  }
        viewModel.searchPieces("Adam")
        val actualResults: List<Piece> = viewModel.piecesToDisplay.getOrAwaitValue()
        assertThat(actualResults.size).isEqualTo(2)
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

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}