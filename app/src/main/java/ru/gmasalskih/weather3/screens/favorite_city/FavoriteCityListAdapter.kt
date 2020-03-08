package ru.gmasalskih.weather3.screens.favorite_city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.databinding.ListItemFavoriteCityBinding

class FavoriteCityListAdapter(private val clickListener: FavoriteCityClickListener) :
    ListAdapter<City, FavoriteCityListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemFavoriteCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: City,
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

    class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    class FavoriteCityClickListener(val clickListener: (city: City) -> Unit) {
        fun onClick(city: City) = clickListener(city)
    }
}

