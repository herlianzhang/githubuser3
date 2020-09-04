package com.latihangoding.consumer.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.latihangoding.consumer.R
import com.latihangoding.consumer.adapters.FavoriteAdapter
import com.latihangoding.consumer.databinding.ActivityMainBinding
import com.latihangoding.consumer.models.FavoriteModel
import com.latihangoding.consumer.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), FavoriteAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = FavoriteAdapter(this)
        binding.rvMain.adapter = adapter

        viewModel.favorites.observe(this, {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.srlMain.setOnRefreshListener {
            viewModel.fetchFavorites()
            binding.srlMain.isRefreshing = false
        }
    }

    override fun onDeleteClick(favorite: FavoriteModel) {
        viewModel.deleteFavorite(favorite.username)
    }
}
