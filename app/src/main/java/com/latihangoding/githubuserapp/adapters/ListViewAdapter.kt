package com.latihangoding.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.latihangoding.githubuserapp.databinding.ListItemBinding
import com.latihangoding.githubuserapp.models.ItemModel
import kotlinx.android.synthetic.main.list_item.view.*


class ListViewAdapter(private val onClickListener: OnClickListener) : ListAdapter<ItemModel, ListViewAdapter.ViewHolder>(
    ListDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(
            parent
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ViewHolder private constructor(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemModel, onClickListener: OnClickListener) {
            binding.model = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClickListener.onListClick(item.login)
            }
            binding.root.ivFavorite.setOnClickListener {
                onClickListener.onFavoriteClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =  LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }

    interface OnClickListener {
        fun onListClick(username: String)
        fun onFavoriteClick(item: ItemModel)
    }
}

class ListDiffCallBack : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
        oldItem == newItem
}