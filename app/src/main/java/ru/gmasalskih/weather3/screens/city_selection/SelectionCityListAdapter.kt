package ru.gmasalskih.weather3.screens.city_selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.databinding.ListItemCitySelectionBinding

class SelectionCityListAdapter(private val clickListener: SelectionCityClickListener) :
    ListAdapter<City, SelectionCityListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemCitySelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: City,
            clickListener: SelectionCityClickListener
        ) {
            binding.city = item
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


    class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.addressLine == newItem.addressLine
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    class SelectionCityClickListener(val clickListener: (citySelection: City) -> Unit) {
        fun onClick(citySelection: City) = clickListener(citySelection)
    }
}