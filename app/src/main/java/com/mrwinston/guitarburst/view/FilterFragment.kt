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
    ): View? {
        _binding = FilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEraDropdown()
        setupDifficultySlider()
    }

    private fun setupDifficultySlider() {
        binding.difficultySlider.values = mutableListOf(1F, 5F)
        binding.difficultySlider.addOnChangeListener { slider, value, fromUser ->
            var primaryColor = 0
            var secondaryColor = 0
            var labelString = ""
            when (value.toInt()) {
                in 1..5 -> { // Beginner
                    primaryColor = getColor(R.color.easy_difficulty_primary)
                    secondaryColor = getColor(R.color.easy_difficulty_secondary)
                    labelString = "Beginner"
                }
                in 6..10 -> { // Intermediate
                    primaryColor = getColor(R.color.indermediate_difficulty_primary)
                    secondaryColor = getColor(R.color.intermediate_difficulty_secondary)
                    labelString = "Intermediate"
                }
                in 11..15 -> { // Advanced
                    primaryColor = getColor(R.color.advanced_difficulty_primary)
                    secondaryColor = getColor(R.color.advanced_difficulty_secondary)
                    labelString = "Advanced"
                }
                in 16..20 -> { // Professional
                    primaryColor = getColor(R.color.professional_difficulty_primary)
                    secondaryColor = getColor(R.color.professional_difficulty_secondary)
                    labelString = "Professional"
                }
            }
            setSliderColors(slider, primaryColor, secondaryColor)
            slider.setLabelFormatter {
                "$labelString\n$value"
            }
        }
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
}