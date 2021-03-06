package com.example.myapplication.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.component.BaseDialogFragment
import com.example.myapplication.di.PrefDataStore
import com.jakewharton.rxbinding4.view.clicks
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    @Inject
    lateinit var dataStore: PrefDataStore

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
                .positiveButton { }
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
            /*actionSubject.onNext(112312312)
            lifecycleScope.launch {
                dataStore.setToken("132131312245425345")
                val token = dataStore.getToken().first()
                println(token)
            }*/
            testRx()
        }

        button_first2.clicks()
            .bindToLifecycle(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribeBy {
                //dataStore.printSomething()
                println(disposable.isDisposed)
            }
    }

    private var disposable  = CompositeDisposable()

    private fun testRx() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .flatMap { value ->
                when (value) {
                    2, 4 -> Observable.create {
                        disposable.dispose()
                    }
                    else -> Observable.create<Int> {
                        it.onNext(value * 2)
                    }
                }
            }
            .subscribeBy(
                onNext = ::println,
                onComplete = { println("completion --- ") },
                onError = Throwable::printStackTrace
            ).addTo(disposable)
    }
}