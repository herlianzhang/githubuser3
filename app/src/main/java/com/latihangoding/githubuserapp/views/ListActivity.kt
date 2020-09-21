package com.latihangoding.githubuserapp.views

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.adapters.ListViewAdapter
import com.latihangoding.githubuserapp.databinding.ActivityListBinding
import com.latihangoding.githubuserapp.di.Injectable
import com.latihangoding.githubuserapp.models.ItemModel
import com.latihangoding.githubuserapp.viewmodels.ListViewModel
import javax.inject.Inject

class ListActivity : AppCompatActivity(), ListViewAdapter.OnClickListener, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
        binding.lifecycleOwner = this

        val adapter =
            ListViewAdapter(this)
        binding.rvMain.adapter = adapter
        binding.viewModel = viewModel

        viewModel.usersModel.observe(this, {
            it?.let {
                adapter.submitList(ArrayList(it))
            }
        })

        viewModel.isError.observe(this, {
            if (it) {
                showErrorMessage()
                viewModel.doneShowErrorMessage()
            }
        })

        viewModel.favorites.observe(this, {
            viewModel.checkFavorite()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemLanguage -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.itemFavorites -> {
                val mIntent = Intent(this, FavoriteListActivity::class.java)
                startActivity(mIntent)
            }
            R.id.itemSetting -> {
                val mIntent = Intent(this, SettingActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onListClick(username: String) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("username", username)
        startActivity(i)
    }

    override fun onFavoriteClick(item: ItemModel) {
        viewModel.setFavorite(item)
    }

    private fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }
}
