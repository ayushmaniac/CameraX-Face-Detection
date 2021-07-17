package com.facedetect.camerax

import android.app.Application
import com.ayush.finbox.di.component.ApplicationComponent
import com.ayush.finbox.di.component.DaggerApplicationComponent
import com.ayush.finbox.di.module.ApplicationModule

class FinboxApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}