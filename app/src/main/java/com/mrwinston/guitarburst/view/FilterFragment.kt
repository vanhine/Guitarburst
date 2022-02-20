package com.mrwinston.guitarburst.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.slider.RangeSlider
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
    ): View {
        _binding = FilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEraDropdown()
        setupDifficultySlider()
        setupLengthDropdown()
        setupSearchButton(view)
    }

    private fun setupSearchButton(view: View) {
        binding.searchButton.setOnClickListener {
            val era = binding.eraSpinner.selectedItem.toString()
            val length = binding.lengthSpinner.selectedItem.toString()
            val difficultyList = binding.difficultySlider.values
            val (minDifficulty, maxDifficulty) = getMinMaxDifficulty(difficultyList)
            val filter = PiecesViewModel.PiecesFilter(era, length, minDifficulty, maxDifficulty)
            piecesViewModel.filterPieces(filter)
            view.findNavController().navigate(R.id.action_moveToSearchResults)
        }
    }

    private fun setupDifficultySlider() {
        binding.difficultySlider.values = mutableListOf(1F, 20F)
        binding.difficultySlider.addOnChangeListener { slider, value, _ ->
            val (primaryColor, secondaryColor) = getSliderColors(value)
            setSliderColors(slider, primaryColor, secondaryColor)
            slider.setLabelFormatter {
                val labelString = getLabelString(it)
                "$labelString\n$it"
            }
        }
    }

    private fun getMinMaxDifficulty(difficultyList: List<Float>): Pair<Int, Int> {
        if (difficultyList.size == 2) {
            return Pair(difficultyList[0].toInt(), difficultyList[1].toInt())
        }
        return Pair(1, 20)
    }

    private fun getSliderColors(value: Float): Pair<Int, Int> {
        when (value) {
            in 1F..5F -> {
                return Pair(
                    getColor(R.color.easy_difficulty_primary),
                    getColor(R.color.easy_difficulty_secondary)
                )
            }
            in 6F..10F -> {
                return Pair(
                    getColor(R.color.indermediate_difficulty_primary),
                    getColor(R.color.intermediate_difficulty_secondary)
                )
            }
            in 11F..15F -> {
                return Pair(
                    getColor(R.color.advanced_difficulty_primary),
                    getColor(R.color.advanced_difficulty_secondary)
                )
            }
            in 16F..20F -> {
                return Pair(
                    getColor(R.color.professional_difficulty_primary),
                    getColor(R.color.professional_difficulty_secondary)
                )
            }
        }
        return Pair(-1, -1)
    }

    private fun getLabelString(value: Float): String {
        when (value) {
            in 1F..5F -> return "Beginner"
            in 6F..10F -> return "Intermediate"
            in 11F..15F -> return "Advanced"
            in 16F..20F -> return "Professional"
        }
        return "Unknown"
    }

    private fun setSliderColors(slider: RangeSlider, primaryColor: Int, secondaryColor: Int) {
        slider.thumbTintList = ColorStateList.valueOf(primaryColor)
        slider.trackTintList = ColorStateList.valueOf(primaryColor)
        slider.trackInactiveTintList = ColorStateList.valueOf(secondaryColor)
    }

    private fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(requireContext(), colorId)
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

    private fun setupLengthDropdown() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.length_dropdown_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.lengthSpinner.adapter = adapter
        }
    }
}
