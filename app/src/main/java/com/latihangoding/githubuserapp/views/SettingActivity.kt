package com.latihangoding.githubuserapp.views

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.latihangoding.githubuserapp.R
import com.latihangoding.githubuserapp.services.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btn_set_once_alarm.setOnClickListener(this)
        btn_cancel_alarm.setOnClickListener(this)
        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(view: View?) {
        view?.let { v ->
            when (v.id) {
                R.id.btn_set_once_alarm -> {
//                    alarmReceiver.showAlarmNotification(this, "Haii", "guys", AlarmReceiver.ID_REPEATING)
                    alarmReceiver.setRepeatingAlarm(this)
                }
                R.id.btn_cancel_alarm -> {
                    alarmReceiver.cancelAlarm(this)
                }
            }
        }
    }
}