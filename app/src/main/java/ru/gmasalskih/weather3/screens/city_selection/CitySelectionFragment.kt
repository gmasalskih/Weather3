package ru.gmasalskih.weather3.screens.city_selection

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
import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.databinding.FragmentCitySelectionBinding

class CitySelectionFragment : Fragment() {

    lateinit var binding: FragmentCitySelectionBinding
    lateinit var viewModel: CitySelectionViewModel
    private lateinit var adapter: SelectionCityListAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitySelectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CitySelectionViewModel::class.java)
        adapter = SelectionCityListAdapter(SelectionCityListAdapter.SelectionCityClickListener {
            onCitySelected(it)
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.selectionCityList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initObserveViewModel()

        binding.enterCityName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.sendGeocoderRequest(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initObserveViewModel() {
        viewModel.responseListCity.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun onCitySelected(city: City) {
        val action = CitySelectionFragmentDirections
            .actionCitySelectionFragmentToWeatherFragment().apply {
                lat = city.lat
                lon = city.lon
            }
        navController.navigate(action)
    }
}