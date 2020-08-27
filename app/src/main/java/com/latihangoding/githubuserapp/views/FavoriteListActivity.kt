package com.latihangoding.githubuserapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.adapters.FavoriteAdapter
import com.latihangoding.githubuserapp.databinding.ActivityFavoriteListBinding
import com.latihangoding.githubuserapp.viewmodels.FavoriteViewModel

class FavoriteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteListBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.my_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_list)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        binding.lifecycleOwner = this

        val adapter = FavoriteAdapter()
        binding.rvMain.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}