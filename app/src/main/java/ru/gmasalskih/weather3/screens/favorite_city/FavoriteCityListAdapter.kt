package ru.gmasalskih.weather3.screens.favorite_city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.databinding.ListItemFavoriteCityBinding

class FavoriteCityListAdapter(private val clickListener: FavoriteCityClickListener) :
    ListAdapter<Location, FavoriteCityListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemFavoriteCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Location,
            clickListener: FavoriteCityClickListener
        ) {
            binding.city = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavoriteCityBinding.inflate(layoutInflater, parent, false)
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

    class FavoriteCityClickListener(val clickListener: (location: Location) -> Unit) {
        fun onClick(location: Location) = clickListener(location)
    }
}

