package com.example.myapplication.ext

import android.view.View
import android.widget.Button
import kotlinx.coroutines.*

//https://medium.com/swlh/android-click-debounce-80b3f2e638f3
//@BindingAdapter("android:onClick")
fun setDebouncedListener(view: Button, onClickListener: View.OnClickListener) {
    val clickWithDebounce: (view: View) -> Unit = debounce {
        onClickListener.onClick(it)
    }
    view.setOnClickListener(clickWithDebounce)
}


fun <T> debounce(
    delayMillis: Long = 500L,
    scope: CoroutineScope = MainScope(),
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (debounceJob == null) {
            debounceJob = scope.launch {
                action(param)
                delay(delayMillis)
                debounceJob = null
            }
        }
    }
}