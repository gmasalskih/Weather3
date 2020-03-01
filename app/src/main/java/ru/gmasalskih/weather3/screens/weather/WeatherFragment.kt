package ru.gmasalskih.weather3.screens.weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding
import ru.gmasalskih.weather3.utils.ObserveLifeCycle
import timber.log.Timber

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding
    lateinit var observeLifeCycle: ObserveLifeCycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.btnCitySelection.setOnClickListener {
            it.findNavController()
                .navigate(WeatherFragmentDirections.actionWeatherFragmentToCitySelectionFragment())
        }
        binding.btnCityWebPage.setOnClickListener {
            it.findNavController()
                .navigate(WeatherFragmentDirections.actionWeatherFragmentToCityWebPageFragment("ya.ru"))
        }
        binding.btnDateSelection.setOnClickListener {
            it.findNavController()
                .navigate(WeatherFragmentDirections.actionWeatherFragmentToDateSelectionFragment())
        }
        setHasOptionsMenu(true)
        observeLifeCycle = ObserveLifeCycle(lifecycle)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("--- onSaveInstanceState")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            view!!.findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}
