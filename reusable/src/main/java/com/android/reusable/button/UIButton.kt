package com.example.reusable.button

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.reusable.R


class UIButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?=null, defStyleAttr: Int =0

): AppCompatButton(context, attrs, defStyleAttr) {

    private var type: Type = Type.Primary

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIButton)
        type = Type.values()[typedArray.getInt(R.styleable.UIButton_type,0)]
        typedArray.recycle()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
       if(type == Type.Secondary){
           when (event?.action){
               MotionEvent.ACTION_DOWN -> setTextColor(
               ContextCompat.getColor(context,R.color.rouge_bui)
               )
               MotionEvent.ACTION_UP -> setTextColor(
                   ContextCompat.getColor(context,R.color.brand_red_bui)
               )
           }
       }
        return super.onTouchEvent(event)
    }

    enum class Type{
       Primary,Secondary
    }
}