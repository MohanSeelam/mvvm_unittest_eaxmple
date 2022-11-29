package com.example.reusable.checkbox


import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.example.reusable.R
import com.example.reusable.databinding.LayoutUiCheckboxBinding

class UICheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: LayoutUiCheckboxBinding =
        LayoutUiCheckboxBinding.inflate(LayoutInflater.from(context), this, true)

    var isChecked = false
          get() = field
          private set(value) {
              field = value
              binding.checkBox.isChecked = value
          }
    var title: String? = null
        set(value) {
            field = value
            if (value == null) {
                binding.componentTitle.visibility = View.GONE
            } else {
                binding.componentTitle.visibility = View.VISIBLE
                binding.componentTitle.text = value
            }
        }
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





    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UICheckBox)

        title = typedArray.getString(R.styleable.UICheckBox_title)
        isChecked = typedArray.getBoolean(R.styleable.UICheckBox_checked,false)
        primaryText = typedArray.getString(R.styleable.UICheckBox_primaryText)
        secondaryText = typedArray.getString(R.styleable.UICheckBox_secondaryText)
        leftImage = typedArray.getDrawable(R.styleable.UICheckBox_leftImage)
        val leftSize = typedArray.getDimensionPixelSize(R.styleable.UICheckBox_leftImageSize, 0)
        if (leftSize > 0) {
            binding.leftImage.updateLayoutParams {
                height = leftSize
                width = leftSize
                requestLayout()
            }
        }

        typedArray.recycle()


    }


}