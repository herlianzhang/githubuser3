package com.latihangoding.consumer

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadAsync()
            }
        }

        contentResolver.registerContentObserver(Values.FAVORITE_URI, true, myObserver)
    }

    private fun loadAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                try {
                    val cursor = contentResolver.query(Values.FAVORITE_URI, null, null, null, null)
                } catch (e: Exception) {
                    Log.e("consumer", "loadAsync: Error lol.")
                }

            }
        }

    }
}
