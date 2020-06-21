package ru.gmasalskih.weather3.screens.location_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jakewharton.rxbinding3.widget.textChangeEvents
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.FragmentLocationSelectionBinding
import ru.gmasalskih.weather3.utils.toast

class LocationSelectionFragment : Fragment() {

    lateinit var binding: FragmentLocationSelectionBinding
    private lateinit var adapter: SelectionLocationsListAdapter
    private lateinit var navController: NavController
    val viewModel: LocationSelectionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationSelectionBinding.inflate(inflater, container, false)
        adapter = SelectionLocationsListAdapter(
            SelectionLocationsListAdapter.SelectionLocationClickListener { onCitySelected(it) }
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.selectionLocationList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initObserveViewModel()
        viewModel.locationFilter(binding.enterLocationName.textChangeEvents())
    }

    private fun initObserveViewModel() {
        viewModel.responseListLocation.observe(viewLifecycleOwner, Observer { listLocations ->
            adapter.submitList(listLocations)
        })
        viewModel.errorMassage.observe(viewLifecycleOwner, Observer {
            it.toast(requireContext())
        })
    }

    private fun onCitySelected(location: Location) {
        val action = LocationSelectionFragmentDirections
            .actionLocationSelectionFragmentToWeatherFragment().apply {
                lat = location.lat
                lon = location.lon
            }
        viewModel.setLastSelectedLocationCoordinates(
            Coordinates(
                lat = location.lat,
                lon = location.lon
            )
        )
        navController.navigate(action)
    }
}