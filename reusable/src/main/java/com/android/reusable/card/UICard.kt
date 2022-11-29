package com.example.reusable.card

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.example.reusable.R
import com.example.reusable.databinding.LayoutUiCardBinding

class UICard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: LayoutUiCardBinding =
        LayoutUiCardBinding.inflate(LayoutInflater.from(context), this, true)

    var primaryText: String? = null
        set(value) {
            field = value
            if (value == null) {
                binding.primaryTextView.visibility = View.GONE
            } else {
                binding.primaryTextView.visibility = View.VISIBLE
                binding.primaryTextView.text = value
            }
        }
    var secondaryText: String? = null
        set(value) {
            field = value
            if (value == null) {
                binding.secondaryTextView.visibility = View.GONE
            } else {
                binding.secondaryTextView.visibility = View.VISIBLE
                binding.secondaryTextView.text = value
            }
        }

    var leftImage: Drawable? = null
        set(value) {
            field = value
            if (value == null) {
                binding.leftImage.visibility = View.GONE
            } else {
                binding.leftImage.visibility = View.VISIBLE
                binding.leftImage.setImageDrawable(value)
            }
        }

    var leftImageSize: Int = 0
        set(value) {
            field = value
            binding.leftImage.updateLayoutParams {
                height = value
                width = value
                requestLayout()
            }
        }

    var buttonText: String? = null
        set(value) {
            field = value
            binding.button.text = value
        }
    var showButton: Boolean = false
        @SuppressLint("SuspiciousIndentation")
        set(value) {
            field = value
            if(value)
                binding.button.visibility = VISIBLE
                binding.leftImage.visibility = GONE
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UICard)
        primaryText = typedArray.getString(R.styleable.UICard_primaryText)
        secondaryText = typedArray.getString(R.styleable.UICard_secondaryText)
        leftImage = typedArray.getDrawable(R.styleable.UICard_leftImage)
        val leftSize = typedArray.getDimensionPixelSize(R.styleable.UICard_leftImageSize, 0)
        if (leftSize > 0) {
            this.leftImageSize = leftImageSize
        }
        showButton = typedArray.getBoolean(R.styleable.UICard_showButton, false)



        buttonText = typedArray.getString(R.styleable.UICard_showButtonText)
        typedArray.recycle()


    }

    fun setButtonClickListener(listener:OnClickListener)=
        binding.button.setOnClickListener(listener)

    fun setTextClickListener(listener:OnClickListener)=
        binding.secondaryTextView.setOnClickListener(listener)


}