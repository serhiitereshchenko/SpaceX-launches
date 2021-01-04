package com.serhii.launches.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sehii.launches.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assignment_activity)
    }
}
