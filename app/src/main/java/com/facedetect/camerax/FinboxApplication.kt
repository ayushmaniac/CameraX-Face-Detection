package com.facedetect.camerax

import android.app.Application
import com.facedetect.camerax.di.component.ApplicationComponent
import com.facedetect.camerax.di.component.DaggerApplicationComponent
import com.facedetect.camerax.di.module.ApplicationModule

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