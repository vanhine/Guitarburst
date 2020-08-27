package com.mrwinston.guitarburst.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.adapters.PiecesListAdapter
import com.mrwinston.guitarburst.data.model.Piece
import com.mrwinston.guitarburst.databinding.ResultsFragmentBinding
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment: Fragment(R.layout.results_fragment) {
    private val piecesViewModel: PiecesViewModel by activityViewModels()
    private lateinit var resultsAdapter: PiecesListAdapter
    private val resultPieces: List<Piece> = emptyList()

    private var _binding: ResultsFragmentBinding? = null
    // Only available between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        val loadingObserver = Observer<Boolean> { isLoading ->
            if(isLoading) {
                showSpinner()
            } else {
                hideSpinner()
            }
        }
        piecesViewModel.isLoading.observe(viewLifecycleOwner, loadingObserver)
        val resultsObserver = Observer<List<Piece>> { results ->
            Log.d("ResultsFragment", "Observer setting results for ${results.size} pieces")
            resultsAdapter.setPieces(results)
        }
        piecesViewModel.piecesToDisplay.observe(viewLifecycleOwner, resultsObserver)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showSpinner() {
        binding.resultsRecycler.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideSpinner() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.resultsRecycler.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.resultsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            resultsAdapter = PiecesListAdapter(context, resultPieces)
            setHasFixedSize(true)
            adapter = resultsAdapter
        }
    }
}