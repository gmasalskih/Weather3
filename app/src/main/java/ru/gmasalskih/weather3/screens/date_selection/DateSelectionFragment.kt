package ru.gmasalskih.weather3.screens.date_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.gmasalskih.weather3.databinding.FragmentDateSelectionBinding

class DateSelectionFragment : Fragment() {

    lateinit var binding: FragmentDateSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateSelectionBinding.inflate(inflater, container, false)
        binding.textView5.setOnClickListener {

        }
        return binding.root
    }

}
