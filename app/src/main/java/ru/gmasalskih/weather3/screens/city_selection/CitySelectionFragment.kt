package ru.gmasalskih.weather3.screens.city_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.databinding.FragmentCitySelectionBinding
import ru.gmasalskih.weather3.utils.toast

class CitySelectionFragment : Fragment() {

    lateinit var binding: FragmentCitySelectionBinding
    lateinit var viewModel: CitySelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitySelectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CitySelectionViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserveViewModel(view)
    }

    private fun initObserveViewModel(view: View) {
        viewModel.isCityConfirmed.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {
                val cityName = binding.enterCityName.text.toString()
                if (viewModel.isEnteredCityValid(cityName) && viewModel.hasEnteredCity(cityName)) {
                    val action = CitySelectionFragmentDirections.actionCitySelectionFragmentToWeatherFragment().apply {
                        setCityName(cityName)
                    }
                    view.findNavController().navigate(action)
                } else{
                    context?.let {
                        "$cityName not faund".toast(context!!)
                    }
                }
            }
        })
    }
}
