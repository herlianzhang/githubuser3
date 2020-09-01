package com.latihangoding.githubuserapp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    val isActive: MutableLiveData<Boolean>
    var isFirst = true
    private val sharedPref = application.applicationContext.getSharedPreferences("My_Preferences", Context.MODE_PRIVATE)

    init {
        isActive = MutableLiveData<Boolean>(sharedPref.getBoolean("DEFAULT_SETTING", false))
    }

    fun setActive() {
        isActive.value?.let { status ->
            val currStatus = !status
            isActive.postValue(currStatus)
            with(sharedPref.edit()) {
                putBoolean("DEFAULT_SETTING", currStatus)
                commit()
            }
        }

    }
}