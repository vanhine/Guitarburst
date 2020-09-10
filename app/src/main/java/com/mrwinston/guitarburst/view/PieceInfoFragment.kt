package com.mrwinston.guitarburst.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.common.flogger.FluentLogger
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.databinding.PieceInfoFragmentBinding
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PieceInfoFragment : Fragment(R.layout.piece_info_fragment) {
    private val piecesViewModel: PiecesViewModel by activityViewModels()
    private var _binding: PieceInfoFragmentBinding? = null

    // Only available between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val logger = FluentLogger.forEnclosingClass()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PieceInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTextViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupTextViews() {
        val piece = piecesViewModel.resultPiece!!
        binding.title.text = piece.title
        binding.composer.text = piece.composer
        binding.difficulty.text = piece.difficulty.toString()
        binding.reading.text = piece.reading.toString()
        binding.position.text = piece.position
        binding.duration.text = piece.duration
        binding.era.text = piece.era
        binding.source.text = piece.source
        binding.publisher.text = piece.publisher
        binding.notes.text = piece.notes

    }
}