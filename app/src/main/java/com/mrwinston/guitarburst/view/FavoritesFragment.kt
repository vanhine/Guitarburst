package com.mrwinston.guitarburst.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.common.flogger.FluentLogger
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.adapters.PiecesListAdapter
import com.mrwinston.guitarburst.data.model.Piece
import com.mrwinston.guitarburst.databinding.FavoritesFragmentBinding
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel

class FavoritesFragment : Fragment(R.layout.favorites_fragment) {
    private val piecesViewModel: PiecesViewModel by activityViewModels()
    private lateinit var resultsAdapter: PiecesListAdapter
    private val favoritePieces: List<Piece> = emptyList()

    private var _binding: FavoritesFragmentBinding? = null

    private val binding get() = _binding!!

    private val logger = FluentLogger.forEnclosingClass()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        val loadingObserver = Observer<Boolean> { isLoading ->
            if (isLoading) {
                showSpinner()
            } else {
                hideSpinner()
            }
        }
        piecesViewModel.isLoading.observe(viewLifecycleOwner, loadingObserver)
        piecesViewModel.populateFavorites()
        val favoritesObserver = Observer<List<Piece>> { results ->
            logger.atInfo().log("Observer setting results for ${results.size} pieces")
            resultsAdapter.setPieces(results)
        }
        piecesViewModel.favoritesToDisplay.observe(viewLifecycleOwner, favoritesObserver)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showSpinner() {
        binding.favoritesRecycler.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideSpinner() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.favoritesRecycler.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.favoritesRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            resultsAdapter =
                PiecesListAdapter(context, favoritePieces, piecesViewModel, viewLifecycleOwner)
            setHasFixedSize(true)
            adapter = resultsAdapter
        }
    }
}