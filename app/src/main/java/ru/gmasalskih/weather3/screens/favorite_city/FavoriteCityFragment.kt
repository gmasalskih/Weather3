package ru.gmasalskih.weather3.screens.favorite_city

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gmasalskih.weather3.databinding.FragmentFavoriteCityBinding

class FavoriteCityFragment : Fragment() {

    lateinit var binding: FragmentFavoriteCityBinding
    lateinit var viewModel: FavoriteCityViewModel
    lateinit var adapter : FavoriteCityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteCityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FavoriteCityViewModel::class.java)
        adapter = FavoriteCityListAdapter(FavoriteCityListAdapter.FavoriteCityClickListener {
            viewModel.onClickFavoriteCity(it)
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.favoriteCityList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favoriteCityList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}
