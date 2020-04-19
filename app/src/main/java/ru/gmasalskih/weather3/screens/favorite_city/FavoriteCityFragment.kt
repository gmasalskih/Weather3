package ru.gmasalskih.weather3.screens.favorite_city

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.FragmentFavoriteCityBinding
import ru.gmasalskih.weather3.utils.toast

class FavoriteCityFragment : Fragment() {

    lateinit var binding: FragmentFavoriteCityBinding
    lateinit var viewModel: FavoriteCityViewModel
    private lateinit var adapter : FavoriteCityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteCityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FavoriteCityViewModel::class.java)
        adapter = FavoriteCityListAdapter(FavoriteCityListAdapter.FavoriteCityClickListener {
            onDeleteFavoriteCity(it)
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.favoriteCityList.adapter = adapter
        return binding.root
    }

    private fun onDeleteFavoriteCity(location: Location){
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("${resources.getText(R.string.del_favorite_city_title)} ${location.name}")
                .setMessage(resources.getText(R.string.del_favorite_city_msg))
                .setPositiveButton(resources.getText(R.string.yes_btn)) { _, _ ->
                    viewModel.onDeleteFavoriteCity(location)
                    "${location.name} ${resources.getText(R.string.msg_favorite_city_deleted)}".toast(it)
                }
                .setNegativeButton(resources.getText(R.string.no_btn), null)
                .create()
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favoriteLocationList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}

