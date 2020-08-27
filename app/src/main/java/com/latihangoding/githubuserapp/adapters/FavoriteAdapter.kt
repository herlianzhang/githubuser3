package com.latihangoding.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databinding.ListFavoriteBinding


class FavoriteAdapter() : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ListFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite) {
            binding.model = item
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListFavoriteBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}

class FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
        oldItem == newItem
}