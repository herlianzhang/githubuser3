package com.latihangoding.consumer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.latihangoding.consumerapp.databases.Favorite
import com.latihangoding.consumerapp.databinding.ListFavoriteBinding


class FavoriteAdapter(private val onClickListener: OnClickListener) : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ViewHolder private constructor(private val binding: ListFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite, onClickListener: OnClickListener) {
            binding.model = favorite
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClickListener.onListClick(favorite.username)
            }
            binding.ivRemove.setOnClickListener {
                onClickListener.onDeleteClick(favorite)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListFavoriteBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    interface OnClickListener {
        fun onListClick(username: String)
        fun onDeleteClick(favorite: Favorite)
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
        oldItem == newItem
}