package com.latihangoding.githubuserapp

import android.app.Application
import com.latihangoding.githubuserapp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GithubApp : Application(), HasAndroidInjector {
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}