package com.latihangoding.githubuserapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.adapters.FavoriteAdapter
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databinding.ActivityFavoriteListBinding
import com.latihangoding.githubuserapp.viewmodels.FavoriteViewModel

class FavoriteListActivity : AppCompatActivity(), FavoriteAdapter.OnClickListener {

    private lateinit var binding: ActivityFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.my_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_list)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        val adapter = FavoriteAdapter(this)
        binding.rvMain.adapter = adapter

        viewModel.favorites.observe(this, {
            it?.let {
               adapter.submitList(it)
            }
        })
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