package ru.gmasalskih.weather3.screens.weather

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.storege.gps.CoordinatesProvider
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
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
        args = WeatherFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(
            this, WeatherViewModelFactory(
                lon = args.lon.toCoordinate(),
                lat = args.lat.toCoordinate(),
                application = requireActivity().application
            )
        ).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initObserveViewModel()
        setHasOptionsMenu(true)
        observeLifeCycle = ObserveLifeCycle(lifecycle)
        viewModel.initCoordinate(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        navController = view.findNavController()
    }


    private fun initObserveViewModel() {

        viewModel.currentCoordinate.observe(viewLifecycleOwner, Observer {
            Timber.i(">>>- $it")
            viewModel.initLocation(lat = it.first, lon = it.second)
        })

        viewModel.currentLocation.observe(viewLifecycleOwner, Observer { location: Location ->
            Timber.i(">>>- $location")
            viewModel.initWeather(location)
                binding.locationName.text = location.name
                val icoFavorite: Int =
                    if (location.isFavorite) R.drawable.ic_favorite_black_24dp else
                        R.drawable.ic_favorite_border_black_24dp
                binding.favoriteLocation.setImageResource(icoFavorite)
        })

        viewModel.currentWeather.observe(viewLifecycleOwner, Observer { weather: Weather ->
            Timber.i(">>>- $weather")
            binding.weatherIcon.setWeatherIcon(requireActivity(), weather.icon)
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
                viewModel.updateCurrentCoordinateFromGPS(this)
                "${resources.getText(R.string.current_location_selected_toast)}".toast(
                    requireContext()
                )
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CoordinatesProvider.PERMISSION_ID && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.updateCurrentCoordinateFromGPS(this)
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

