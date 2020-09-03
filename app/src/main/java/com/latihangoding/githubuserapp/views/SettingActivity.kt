package com.latihangoding.githubuserapp.views

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.databinding.ActivitySettingBinding
import com.latihangoding.githubuserapp.services.AlarmReceiver
import com.latihangoding.githubuserapp.viewmodels.SettingViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingBinding
    private lateinit var receiver: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        alarmReceiver = AlarmReceiver()
        receiver = ComponentName(this, AlarmReceiver::class.java)

        viewModel.isActive.observe(this, { status ->
            if (viewModel.isFirst) {
                viewModel.isFirst = false
                return@observe
            }
            if (status) {
                packageManager.setComponentEnabledSetting(
                    receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )
                alarmReceiver.setRepeatingAlarm(this)
            } else {
                packageManager.setComponentEnabledSetting(
                    receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
                alarmReceiver.cancelAlarm(this)
            }
        })
    }
}