package ru.gmasalskih.weather3.screens.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.databinding.FragmentSettingsBinding
import ru.gmasalskih.weather3.utils.toast

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        viewModel.message.observe(viewLifecycleOwner, Observer { it.toast(requireContext()) })
        binding.removeLocationBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(resources.getText(R.string.clear_all_locations_title))
                .setMessage(resources.getText(R.string.clear_all_locations_msg))
                .setPositiveButton(resources.getText(R.string.yes_btn)) { _, _ ->
                    viewModel.onClearHistory()
                }.setNegativeButton(resources.getText(R.string.no_btn), null)
                .create()
                .show()
        }

        binding.removeFavoriteBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(resources.getText(R.string.del_all_favorites_locations_title))
                .setMessage(resources.getText(R.string.del_all_favorites_locations_msg))
                .setPositiveButton(resources.getText(R.string.yes_btn)) { _, _ ->
                    viewModel.onRemoveFavoritesLocations()
                }.setNegativeButton(resources.getText(R.string.no_btn), null)
                .create()
                .show()
        }
    }
}