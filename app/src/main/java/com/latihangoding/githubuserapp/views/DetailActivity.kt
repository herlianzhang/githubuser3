package com.latihangoding.githubuserapp.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.databinding.ActivityDetailBinding
import com.latihangoding.githubuserapp.fragments.DetailFragment
import com.latihangoding.githubuserapp.viewmodels.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra("username") ?: ""
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.vpProfile.adapter = ScreenSliderPagerAdapter()
        binding.vpProfile.isUserInputEnabled = false
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getProfile(username)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isBackClicked.observe(this, { isBack ->
            if (isBack) {
                onBackPressed()
            }
        })

        binding.BNMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.followers -> binding.vpProfile.currentItem = 0
                R.id.following -> binding.vpProfile.currentItem = 1
            }

            return@setOnNavigationItemSelectedListener true
        }

        viewModel.isFavorite.observe(this, {
            binding.ibFavorite.setImageResource(if (it) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
        })

        viewModel.profileModel.observe(this, {
            viewModel.checkFavorite()
        })


        viewModel.isError.observe(this, {
            if (it) {
                showErrorMessage()
                viewModel.doneShowErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    inner class ScreenSliderPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment =
            DetailFragment.newInstance(username, position == 0)
    }
}