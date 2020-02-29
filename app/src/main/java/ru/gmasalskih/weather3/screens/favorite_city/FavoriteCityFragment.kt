package ru.gmasalskih.weather3.screens.favorite_city

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gmasalskih.weather3.databinding.FragmentFavoriteCityBinding

class FavoriteCityFragment : Fragment() {

    lateinit var binding: FragmentFavoriteCityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteCityBinding.inflate(inflater, container, false)
        return binding.root
    }

}
