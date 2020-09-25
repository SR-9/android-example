package com.example.myapplication.widget


import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.ext.dp
import com.google.android.material.button.MaterialButton

class RaiseButton @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = R.attr.materialButtonStyle) : MaterialButton(context, attributes, defStyleAttr) {

    init {
        cornerRadius = 20.dp
    }

}