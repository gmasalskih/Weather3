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
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.CitySelection
import ru.gmasalskih.weather3.databinding.FragmentCitySelectionBinding
import ru.gmasalskih.weather3.utils.toast
import timber.log.Timber

class CitySelectionFragment : Fragment() {

    lateinit var binding: FragmentCitySelectionBinding
    lateinit var viewModel: CitySelectionViewModel
    lateinit var adapter: SelectionCityListAdapter

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
        initObserveViewModel(view)
        Timber.i("--- AAA")
        viewModel.responseListCity.observe(viewLifecycleOwner, Observer {
            Timber.i("--- AAA$it")
            adapter.submitList(it)
        })

        binding.enterCityName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //NOP
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //NOP
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getResponse(s.toString())
            }
        })
    }

    private fun onCitySelected(citySelection: CitySelection){
        context?.let {
            "$citySelection".toast(it)
        }
    }

    private fun initObserveViewModel(view: View) {
        viewModel.isCityConfirmed.observe(viewLifecycleOwner, Observer { event: Boolean ->
            if (event) {

                val cityName = binding.enterCityName.text.toString()
                viewModel.getResponse(cityName)
//                if (viewModel.isEnteredCityValid(cityName) && viewModel.hasEnteredCity(cityName)) {
//                    val action =
//                        CitySelectionFragmentDirections.actionCitySelectionFragmentToWeatherFragment()
//                            .apply {
//                                setCityName(cityName)
//                            }
//                    view.findNavController().navigate(action)
//                } else {
//                    context?.let {
//                        "$cityName ${resources.getText(R.string.city_not_found)}".toast(it)
//                    }
//                }


            }
        })

//        viewModel.response.observe(viewLifecycleOwner, Observer { event: String? ->
//            event?.let {
//                Timber.i("--- $it")
//            }
//
//        })

    }
}
