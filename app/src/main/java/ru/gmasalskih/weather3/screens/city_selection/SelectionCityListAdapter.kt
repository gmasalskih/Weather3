package ru.gmasalskih.weather3.screens.city_selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gmasalskih.weather3.data.CitySelection
import ru.gmasalskih.weather3.databinding.ListItemCitySelectionBinding

class SelectionCityListAdapter(private val clickListener: SelectionCityClickListener) :
    ListAdapter<CitySelection, SelectionCityListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemCitySelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: CitySelection,
            clickListener: SelectionCityClickListener
        ) {
            binding.citySelection = item
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCitySelectionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<CitySelection>() {
        override fun areItemsTheSame(oldItem: CitySelection, newItem: CitySelection): Boolean {
            return oldItem.addressLine == newItem.addressLine
        }

        override fun areContentsTheSame(oldItem: CitySelection, newItem: CitySelection): Boolean {
            return oldItem == newItem
        }
    }

    class SelectionCityClickListener(val clickListener: (citySelection: CitySelection) -> Unit) {
        fun onClick(citySelection: CitySelection) = clickListener(citySelection)
    }
}