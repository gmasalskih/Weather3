package ru.gmasalskih.weather3.screens.city_web_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.databinding.FragmentCityWebPageBinding

class CityWebPageFragment : Fragment() {

    lateinit var binding: FragmentCityWebPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityWebPageBinding.inflate(inflater, container, false)
        binding.textView6.setOnClickListener {

        }
        return binding.root
    }
}