package com.mrwinston.guitarburst.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.databinding.FilterFragmentBinding
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment(R.layout.filter_fragment) {
    private val piecesViewModel: PiecesViewModel by activityViewModels()
    private var _binding: FilterFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEraDropdown()
    }

    private fun setupEraDropdown() {
        // TODO(vanhine): This currently uses a static list of options. Although we don't plan on
        // having updates to this data, it would probably be good to read this from the database
        // using a cursor adapter.
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.era_dropdown_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.eraSpinner.adapter = adapter
        }
    }
}