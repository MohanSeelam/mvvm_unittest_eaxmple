package com.android.mvvmunittesteaxmple.utils

import android.view.View
import com.example.reusable.util.SafeClickListener

fun View.setSafeOnClickListener(onSafeClick:(View) -> Unit){
    val safeClickListener = SafeClickListener({onSafeClick(it)})
    setOnClickListener(safeClickListener)
}
fun View.setSafeOnClickListener(onClickListener: View.OnClickListener?){
    setSafeOnClickListener {onClickListener?.onClick(it) }
}

inline fun View.onDebouncedListener(delayInClick:Long=500L,crossinline listener:(View) -> Unit){

    val enableAgain = Runnable{ isEnabled = true}

    setOnClickListener {
        if(isEnabled){
            isEnabled = false
            postDelayed(enableAgain,delayInClick)
            listener(it)
        }
    }
}