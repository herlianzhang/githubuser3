package com.latihangoding.githubuserapp.di

import com.latihangoding.githubuserapp.views.FavoriteListActivity
import com.latihangoding.githubuserapp.views.ListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFavoriteActivity(): FavoriteListActivity

    @ContributesAndroidInjector
    abstract fun contributeListActivity(): ListActivity
}