package ru.gmasalskih.weather3.screens.city_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.databinding.FragmentCitySelectionBinding

class CitySelectionFragment : Fragment() {

    lateinit var binding: FragmentCitySelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitySelectionBinding.inflate(inflater, container, false)
        binding.textView7.setOnClickListener {

        }
        return binding.root
    }

}
