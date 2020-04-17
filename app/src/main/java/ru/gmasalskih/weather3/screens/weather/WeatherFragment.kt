package ru.gmasalskih.weather3.screens.weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding
import ru.gmasalskih.weather3.utils.ObserveLifeCycle
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding
    private lateinit var observeLifeCycle: ObserveLifeCycle
    private lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var navController: NavController
    private lateinit var viewModel: WeatherViewModel
    private lateinit var args: WeatherFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        //TODO() STUB! fix later
        arguments?.let {
            args = WeatherFragmentArgs.fromBundle(it)
        }
        viewModelFactory = WeatherViewModelFactory(
            lon = args.lon,
            lat = args.lat
        )
        //

        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        observeLifeCycle = ObserveLifeCycle(lifecycle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        viewModel.updateFavoriteCityStatus()
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        viewModel.isCityFavoriteSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            Timber.i("$TAG_LOG $event")
            val icoFavorite: Int =
                if (event) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
            binding.favoriteCity.setImageResource(icoFavorite)
        })

        viewModel.currentLocation.observe(viewLifecycleOwner, Observer { city: City ->
            binding.cityName.text = city.name
        })

        viewModel.isDateSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.currentWeather.value?.let { weather: Weather ->
                    val action = WeatherFragmentDirections
                        .actionWeatherFragmentToDateSelectionFragment(weather.city.name)
                    navController.navigate(action)
                }
            }
        })

        viewModel.isCitySelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                val action = WeatherFragmentDirections
                    .actionWeatherFragmentToCitySelectionFragment()
                navController.navigate(action)
            }
        })

        viewModel.isCityWebPageSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.currentWeather.value?.let { weather: Weather ->
                    val action = WeatherFragmentDirections
                        .actionWeatherFragmentToCityWebPageFragment(weather.city.url)
                    navController.navigate(action)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }
}