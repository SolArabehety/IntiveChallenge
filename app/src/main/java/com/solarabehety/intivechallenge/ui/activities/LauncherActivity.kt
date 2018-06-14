package com.solarabehety.intivechallenge.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.solarabehety.intivechallenge.R
import java.util.*
import kotlin.concurrent.schedule

class LauncherActivity : AppCompatActivity() {
    private val LAUNCHER_TIME = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_launcher)

        Timer("start_main", true).schedule(LAUNCHER_TIME) {
            startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
        }


    }
}
