package com.latihangoding.githubuserapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.latihangoding.githubuserapp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}