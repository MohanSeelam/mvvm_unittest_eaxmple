package com.example.reusable.util

import android.os.SystemClock
import android.view.View

class SafeClickListener(private val onSafeClick:(View)-> Unit,
private val defaultInterval :Int =1000):View.OnClickListener {
    private var lastTimeClicked:Long =0
    override fun onClick(v: View) {
        if(SystemClock.elapsedRealtime()-lastTimeClicked < defaultInterval){
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }


}