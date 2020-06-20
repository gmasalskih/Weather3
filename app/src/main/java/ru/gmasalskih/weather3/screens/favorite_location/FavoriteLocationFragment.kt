package ru.gmasalskih.weather3.screens.favorite_location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.FragmentFavoriteLocationBinding
import ru.gmasalskih.weather3.utils.toast

class FavoriteLocationFragment : Fragment() {

    lateinit var binding: FragmentFavoriteLocationBinding
    val viewModel: FavoriteLocationViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var adapter: FavoriteLocationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteLocationBinding.inflate(inflater, container, false)
        adapter =
            FavoriteLocationListAdapter(
                FavoriteLocationListAdapter.FavoriteLocationClickListener(
                    ::onDeleteFavoriteLocation,
                    ::onSelectClickListener
                )
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.favoriteLocationsList.adapter = adapter
        return binding.root
    }

    private fun onSelectClickListener(location: Location) {
        val action = FavoriteLocationFragmentDirections
            .actionFavoriteLocationFragmentToWeatherFragment().apply {
                lat = location.lat
                lon = location.lon
            }
        viewModel.setLastSelectedLocationCoordinates(
            coordinates = Coordinates(lat = location.lat, lon = location.lon)
        )
        navController.navigate(action)
    }

    private fun onDeleteFavoriteLocation(location: Location) {
        AlertDialog.Builder(requireContext())
            .setTitle("${resources.getText(R.string.del_favorite_location_title)} ${location.name}")
            .setMessage(resources.getText(R.string.del_favorite_location_msg))
            .setPositiveButton(resources.getText(R.string.yes_btn)) { _, _ ->
                viewModel.onDeleteFavoriteCity(location)
            }.setNegativeButton(resources.getText(R.string.no_btn), null)
            .create()
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        viewModel.favoriteLocationList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.massage.observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })

        viewModel.errorMassage.observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })
    }
}

