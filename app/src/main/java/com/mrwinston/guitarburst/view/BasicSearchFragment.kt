package com.mrwinston.guitarburst.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.chip.ChipGroup
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.databinding.BasicSearchFragmentBinding
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasicSearchFragment : Fragment(R.layout.basic_search_fragment) {
    private val piecesViewModel: PiecesViewModel by activityViewModels()
    private var _binding: BasicSearchFragmentBinding? = null

    // Only available between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BasicSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSearchButton(view)
        setupChipGroup()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupSearchButton(view: View) {
        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            piecesViewModel.searchPieces(searchText)
            view.findNavController().navigate(R.id.action_moveToSearchResults)
        }
    }

    private fun setupChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { _: ChipGroup, checkedId: Int ->
            when (checkedId) {
                R.id.title_chip -> piecesViewModel.checkedCategory = PiecesViewModel.SearchCategory.TITLE
                R.id.composer_chip -> piecesViewModel.checkedCategory = PiecesViewModel.SearchCategory.COMPOSER
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}