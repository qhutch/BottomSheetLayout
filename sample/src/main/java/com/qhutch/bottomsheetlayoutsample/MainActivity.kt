package com.qhutch.bottomsheetlayoutsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qhutch.bottomsheetlayout.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomSheetLayout.setOnClickListener { _ -> bottomSheetLayout.toggle() }
        bottomSheetLayout.setOnProgressListener { progress -> rotateArrow(progress)}
    }

    private fun rotateArrow(progress: Float) {
        imageViewArrow.rotation = 180 * progress - 180
    }
}
