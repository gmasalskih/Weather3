package ru.gmasalskih.weather3.screens.weather

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding
import ru.gmasalskih.weather3.utils.*

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding
    private lateinit var observeLifeCycle: ObserveLifeCycle
    private lateinit var navController: NavController
    private lateinit var args: WeatherFragmentArgs
    private lateinit var alertDialogNavigate :AlertDialog

    val viewModel: WeatherViewModel by viewModel {
        parametersOf(Coordinates(lat = args.lat.toCoordinate(), lon = args.lon.toCoordinate()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        args = WeatherFragmentArgs.fromBundle(requireArguments())
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initObserveViewModel()
        setHasOptionsMenu(true)
        observeLifeCycle = ObserveLifeCycle(lifecycle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        navController = view.findNavController()
        alertDialogNavigate = createAlertDialogNavigate()
        viewModel.initCoordinates()
    }

    override fun onStop() {
        super.onStop()
        alertDialogNavigate.hide()
    }

    private fun createAlertDialogNavigate() =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_is_emty_current_location)
            .setMessage(R.string.msg_is_emty_current_location)
            .setCancelable(false)
            .setPositiveButton(R.string.select_location_btn_is_emty_current_location) { _, _ ->
                val action = WeatherFragmentDirections
                    .actionWeatherFragmentToLocationSelectionFragment()
                navController.navigate(action)
            }.create()

    private fun initObserveViewModel() {
        viewModel.isGPSAlloy.observe(viewLifecycleOwner, Observer { event: Boolean ->
            binding.currentLocation.visibility = if (event) View.VISIBLE else View.GONE
        })

        viewModel.isCurrentCoordinateEmpty.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) alertDialogNavigate.show() else alertDialogNavigate.hide()
        })

        viewModel.errorMassage.observe(viewLifecycleOwner, Observer {
            it.toast(requireContext())
        })

        viewModel.isLocationFavorite.observe(
            viewLifecycleOwner, Observer { event: Boolean ->
                val icoFavorite: Int =
                    if (event) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
                binding.favoriteLocation.setImageResource(icoFavorite)
            })

        viewModel.currentLocation.observe(viewLifecycleOwner, Observer { location: Location ->
            binding.locationName.text = location.name
        })

        viewModel.currentWeather.observe(viewLifecycleOwner, Observer { weather: Weather ->
            binding.weatherIcon.setWeatherIcon(requireActivity(), weather.icon)
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
            if (event) viewModel.retrieveLastKnownCoordinate()
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