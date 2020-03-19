package ru.gmasalskih.weather3.screens.weather

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.Weather
import ru.gmasalskih.weather3.databinding.FragmentWeatherBinding
import ru.gmasalskih.weather3.utils.ObserveLifeCycle
import timber.log.Timber

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding
    private lateinit var observeLifeCycle: ObserveLifeCycle
    private lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel
    private lateinit var args: WeatherFragmentArgs

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("--- onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("--- onCreate")
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("--- onCreateView")
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        //TODO() STUB! fix later
        arguments?.let {
            args = WeatherFragmentArgs.fromBundle(it)
        }
        viewModelFactory = WeatherViewModelFactory(args.cityName, args.date)
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
        Timber.i("--- onViewCreated")
        viewModel.updateFavoriteCityStatus()
        initObserveViewModel(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.i("--- onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("--- onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("--- onStart")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("--- onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("--- onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("--- onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.i("--- onDetach")
    }

    private fun initObserveViewModel(view: View){
        viewModel.isCityFavoriteSelected.observe(viewLifecycleOwner, Observer {event: Boolean ->
            Timber.i("--- !!!${event}")
            if(event){
                binding.favoriteCity.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                binding.favoriteCity.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        })

        viewModel.isDateSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.currentWeather.value?.let { weather: Weather ->
                    view.findNavController()
                        .navigate(
                            WeatherFragmentDirections.actionWeatherFragmentToDateSelectionFragment(
                                weather.city.name
                            )
                        )
                }
            }
        })

        viewModel.isCitySelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                view.findNavController()
                    .navigate(WeatherFragmentDirections.actionWeatherFragmentToCitySelectionFragment())
            }
        })

        viewModel.isCityWebPageSelected.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                viewModel.currentWeather.value?.let { weather: Weather ->
                    view.findNavController().navigate(
                        WeatherFragmentDirections.actionWeatherFragmentToCityWebPageFragment(weather.city.url)
                    )
                }
            }
        })
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