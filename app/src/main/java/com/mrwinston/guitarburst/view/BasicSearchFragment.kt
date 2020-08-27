package com.mrwinston.guitarburst.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrwinston.guitarburst.R
import com.mrwinston.guitarburst.adapters.PiecesAdapter
import com.mrwinston.guitarburst.data.model.Piece
import com.mrwinston.guitarburst.databinding.BasicSearchFragmentBinding
import com.mrwinston.guitarburst.viewmodel.PiecesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.basic_search_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class BasicSearchFragment: Fragment(R.layout.basic_search_fragment) {
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
        binding.editButton.setOnClickListener {
            piecesViewModel.searchPieces(binding.searchEditText.text.toString())
            view.findNavController().navigate(R.id.action_moveToSearchResults)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}