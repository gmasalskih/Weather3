package ru.gmasalskih.weather3.screens.location_selection

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.FragmentLocationSelectionBinding

class LocationSelectionFragment : Fragment() {

    lateinit var binding: FragmentLocationSelectionBinding
    lateinit var viewModel: LocationSelectionViewModel
    private lateinit var adapter: SelectionLocationsListAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationSelectionBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(
                this,
                LocationSelectionViewModelFactory(application = requireActivity().application)
            )
                .get(LocationSelectionViewModel::class.java)

        adapter =
            SelectionLocationsListAdapter(SelectionLocationsListAdapter.SelectionLocationClickListener {
                onCitySelected(it)
            })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.selectionLocationList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initObserveViewModel()

        binding.enterLocationName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.sendGeocoderRequest(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initObserveViewModel() {
        viewModel.listLocation.observe(viewLifecycleOwner, Observer { listLocations ->
            adapter.submitList(listLocations)
        })
        viewModel.responseListLocation.observe(viewLifecycleOwner, Observer { listLocations ->
            adapter.submitList(listLocations)
        })
    }

    private fun onCitySelected(location: Location) {
        viewModel.addSelectedLocationToDB(location)
        val action = LocationSelectionFragmentDirections
            .actionLocationSelectionFragmentToWeatherFragment().apply {
                lat = location.lat
                lon = location.lon
            }
        viewModel.setLastSelectedLocationCoordinates(lat = location.lat, lon = location.lon)
        navController.navigate(action)
    }
}