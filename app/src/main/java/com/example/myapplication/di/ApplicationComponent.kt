package com.example.myapplication.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component()
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): ApplicationComponent
    }
}
