package com.example.yackeensolutionstask.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yackeensolutionstask.R

class internetConnection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_connection)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}