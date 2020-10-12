package com.example.myapplication.component

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.dialog_view.view.*

open class BaseDialogFragment(private val activity: AppCompatActivity, isCancel: Boolean = false) : DialogFragment() {

    private val root = LayoutInflater.from(activity).inflate(R.layout.dialog_view, null, false)

    init {
        isCancelable = isCancel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.attributes?.apply {
            val metrics = resources.displayMetrics
            width = (metrics.widthPixels * 0.9).toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    activity,
                    R.drawable.background_white_rounded_corner_8dp
                )
            )
            setOnKeyListener { _, i, _ ->
                if (isCancelable && i == KeyEvent.KEYCODE_BACK) {
                    dismiss()
                    //requireActivity().finish()
                }
                return@setOnKeyListener true
            }
        }
        return root
    }

    open fun setTitle(@StringRes title: Int): BaseDialogFragment {
        return setTitle(activity.getString(title))
    }

    open fun setTitle(title: CharSequence?): BaseDialogFragment {
        root.title.apply {
            this.text = title
            this.visibility = View.VISIBLE
        }
        return this
    }

    open fun negativeButton(text: Int, action: () -> Unit = {}): BaseDialogFragment {
        return negativeButton(activity.getString(text), action)
    }

    open fun negativeButton(
        text: CharSequence? = "CANCEL",
        action: () -> Unit = {}
    ): BaseDialogFragment {
        root.negativeButton.apply {
            visibility = View.VISIBLE
            this.text = text
            setOnClickListener {
                dismiss()
                action.invoke()
            }
        }
        return this
    }

    open fun positiveButton(text: Int, action: () -> Unit = {}): BaseDialogFragment {
        return positiveButton(activity.getString(text), action)
    }

    open fun positiveButton(
        text: CharSequence? = "OK",
        action: () -> Unit = {}
    ): BaseDialogFragment {
        root.positiveButton.apply {
            visibility = View.VISIBLE
            this.text = text
            setOnClickListener {
                dismiss()
                action.invoke()
            }
        }
        return this
    }

    open fun setContentText(@StringRes content: Int) : BaseDialogFragment {
        return setContentText(activity.getString(content))
    }

    open fun setContentText(content: CharSequence?) : BaseDialogFragment {
        root.content.text = content
        return this
    }

    open fun customView(view: View) : BaseDialogFragment {
        root.contentLayout.apply {
            this.removeAllViews()
            this.addView(view)
        }

        return  this
    }

    open fun show() {
        show(activity.supportFragmentManager, "BaseDialogFragment")
    }
}