package ru.gmasalskih.weather3.screens.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.btnCitySelection.setOnClickListener {
            it.findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCitySelectionFragment())
        }
        binding.btnCityWebPage.setOnClickListener {
            it.findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCityWebPageFragment())
        }
        binding.btnDateSelection.setOnClickListener {
            it.findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToDateSelectionFragment())
        }
        return binding.root
    }
}