package ru.gmasalskih.weather3.screens.weather

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.storege.gps.CoordinatesProvider
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding
import ru.gmasalskih.weather3.utils.*
import timber.log.Timber

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding
    private lateinit var observeLifeCycle: ObserveLifeCycle
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
        activity?.let {
            viewModel = ViewModelProvider(
                this, WeatherViewModelFactory(
                    lon = args.lon.toCoordinate(),
                    lat = args.lat.toCoordinate(),
                    application = it.application
                )
            ).get(WeatherViewModel::class.java)
        }
        //

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initObserveViewModel()
        setHasOptionsMenu(true)
        observeLifeCycle = ObserveLifeCycle(lifecycle)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        navController = view.findNavController()
        initLocation()
    }

    private fun initLocation() {
        if (args.lat == EMPTY_COORDINATE || args.lon == EMPTY_COORDINATE) {
            val _lat = viewModel.getLastLocationLat()
            val _lon = viewModel.getLastLocationLon()
            if (_lat == EMPTY_COORDINATE || _lon == EMPTY_COORDINATE) {
                Timber.i("ZZZ--- initLocation")
                viewModel.initCoordinates(this)
                return
            } else {
                viewModel.lat = _lat
                viewModel.lon = _lon
            }
        }
        viewModel.initLocation()
    }

    private fun initObserveViewModel() {
        viewModel.isLocationFavoriteSelected.observe(
            viewLifecycleOwner, Observer { event: Boolean ->
                Timber.i("$TAG_LOG $event")
                val icoFavorite: Int =
                    if (event) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
                binding.favoriteLocation.setImageResource(icoFavorite)
            })

        viewModel.currentLocation.observe(viewLifecycleOwner, Observer { location: Location ->
            binding.locationName.text = location.name
            viewModel.showLastSelectedLocationCoordinates()
        })

        viewModel.currentWeather.observe(viewLifecycleOwner, Observer { weather: Weather ->
            activity?.let { activity: FragmentActivity ->
                binding.weatherIcon.setWeatherIcon(activity, weather.icon)
            }
        })

        viewModel.isDateSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.currentLocation.value?.let { location: Location ->
                    val action = WeatherFragmentDirections
                        .actionWeatherFragmentToDateSelectionFragment(location.name)
                    navController.navigate(action)
                }
            }
        })

        viewModel.isLocationSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                val action = WeatherFragmentDirections
                    .actionWeatherFragmentToLocationSelectionFragment()
                navController.navigate(action)
            }
        })

        viewModel.isLocationWebPageSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.currentWeather.value?.let { weather: Weather ->
                    val action = WeatherFragmentDirections
                        .actionWeatherFragmentToLocationWebPageFragment(weather.url)
                    navController.navigate(action)
                }
            }
        })

        viewModel.isCurrentLocationSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.initCoordinates(this)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Timber.i("GPS--- onRequestPermissionsResult")
        if (requestCode == CoordinatesProvider.PERMISSION_ID && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.initCoordinates(this)
        }
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

