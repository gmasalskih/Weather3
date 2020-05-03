package ru.gmasalskih.weather3.screens.favorite_location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.ListItemFavoriteLocationBinding

class FavoriteLocationListAdapter(private val clickListener: FavoriteLocationClickListener) :
    ListAdapter<Location, FavoriteLocationListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemFavoriteLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Location,
            clickListener: FavoriteLocationClickListener
        ) {
            binding.location = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavoriteLocationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
        }
    }

    class FavoriteLocationClickListener(val onDeleteClickListener: (location: Location) -> Unit, val onSelectClickListener: (location: Location) -> Unit) {
        fun onDelete(location: Location) = onDeleteClickListener(location)
        fun onSelect(location: Location) = onSelectClickListener(location)
    }
}

