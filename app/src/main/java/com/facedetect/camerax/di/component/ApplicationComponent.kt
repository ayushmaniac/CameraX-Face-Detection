package com.ayush.finbox.di.component

import android.content.Context
import com.ayush.finbox.di.module.ApplicationModule
import com.facedetect.camerax.FinboxApplication
import com.facedetect.camerax.data.ApplicationRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: FinboxApplication)

    fun getContext() : Context

    fun getAppRepository() : ApplicationRepository


}