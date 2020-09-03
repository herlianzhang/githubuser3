package com.latihangoding.githubuserapp.views

import android.content.ComponentName
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.util.CursorUtil
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databinding.ActivitySettingBinding
import com.latihangoding.githubuserapp.services.AlarmReceiver
import com.latihangoding.githubuserapp.viewmodels.SettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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


        GlobalScope.launch(Dispatchers.IO) {
            val test = Uri.parse("content://com.latihangoding.githubuserapp.provider/favorite_table/1")
            val test1 = contentResolver.query(test,  null, null, null, null)
//            val test2 = test1?.getString(test1.getColumnIndexOrThrow(Favorite.USERNAME))
            Log.d("WOII", "onCreate: Columncount${test1?.columnCount}\ncount: ${test1?.count}")
        }

    }
}