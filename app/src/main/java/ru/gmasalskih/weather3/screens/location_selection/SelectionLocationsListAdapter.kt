package ru.gmasalskih.weather3.screens.location_selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.ListItemLocationSelectionBinding

class SelectionLocationsListAdapter(private val clickListener: SelectionLocationClickListener) :
    ListAdapter<Location, SelectionLocationsListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemLocationSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Location,
            clickListener: SelectionLocationClickListener
        ) {
            binding.location = item
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLocationSelectionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.addressLine == newItem.addressLine
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    class SelectionLocationClickListener(val clickListener: (locationSelection: Location) -> Unit) {
        fun onClick(locationSelection: Location) = clickListener(locationSelection)
    }
}