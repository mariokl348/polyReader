package com.company.epubreader.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.company.epubreader.R
import com.company.epubreader.ui.register.RegisterActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Timer().schedule(1500) {

            startActivity(
                Intent(this@SplashActivity,RegisterActivity::class.java)
            )

            finish()


        }
    }
}