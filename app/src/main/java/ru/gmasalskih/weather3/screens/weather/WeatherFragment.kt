package ru.gmasalskih.weather3.screens.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.textView.setOnClickListener {
            it.findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToAboutFragment())
        }
        return binding.root
    }
}