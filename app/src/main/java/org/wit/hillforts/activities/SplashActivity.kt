package org.wit.hillforts.activities

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.R

class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed(
            {
                run {
                    startActivityForResult<LoginActivity>(0)

                    // close this activity
                    finish()
                }
            },
            SPLASH_TIME_OUT
        )
    }
}