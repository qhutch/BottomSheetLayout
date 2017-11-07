package com.qhutch.bottomsheetlayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomSheetLayout.setOnClickListener { _ -> bottomSheetLayout.toggle() }
        bottomSheetLayout.setOnProgressListener { progress -> logProgress(progress)}
    }

    private fun logProgress(progress: Float) {
        Log.d("PROGRESS", "progress is $progress")
    }
}
