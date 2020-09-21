package com.latihangoding.githubuserapp.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.adapters.FavoriteAdapter
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databinding.ActivityFavoriteListBinding
import com.latihangoding.githubuserapp.di.Injectable
import com.latihangoding.githubuserapp.viewmodels.FavoriteViewModel
import javax.inject.Inject

class FavoriteListActivity : AppCompatActivity(), FavoriteAdapter.OnClickListener, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_list)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = FavoriteAdapter(this)
        binding.rvMain.adapter = adapter

        viewModel.favorites.observe(this, {
            it?.let {
                adapter.submitList(it)
            }
        })

        title = getString(R.string.my_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onListClick(username: String) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("username", username)
        startActivity(i)
    }

    override fun onDeleteClick(favorite: Favorite) {
        viewModel.deleteFavorite(favorite)
    }

}