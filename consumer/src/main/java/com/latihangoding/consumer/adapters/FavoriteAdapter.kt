package com.latihangoding.consumer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.latihangoding.consumer.databinding.ListFavoriteBinding
import com.latihangoding.consumer.models.FavoriteModel


class FavoriteAdapter(private val onClickListener: OnClickListener) : ListAdapter<FavoriteModel, FavoriteAdapter.ViewHolder>(
    FavoriteDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ViewHolder private constructor(private val binding: ListFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteModel, onClickListener: OnClickListener) {
            binding.model = favorite
            binding.executePendingBindings()
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
        fun onDeleteClick(favorite: FavoriteModel)
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteModel>() {
    override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean =
        oldItem == newItem
}