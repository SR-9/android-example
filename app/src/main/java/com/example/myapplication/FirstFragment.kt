package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.component.BaseDialogFragment
import com.jakewharton.rxbinding4.view.clicks
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            BaseDialogFragment(activity = requireActivity() as AppCompatActivity)
                .apply { isCancelable = false }
                .setTitle("my title app")
                .setContentText("my content app")
                .positiveButton {  }
                .show()
        }

        val actionSubject = PublishSubject.create<Any>()

        arrayListOf(1, 2, 3, 4, 5).toObservable()
            .bindToLifecycle(this)
            .subscribeBy {
                println(it)
            }

        actionSubject.bindToLifecycle(this).subscribeBy {
            println(it)
        }
        button_first1.setOnClickListener {
            actionSubject.onNext(112312312)
        }

        button_first2.clicks()
            .bindToLifecycle(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribeBy {  }
    }
}