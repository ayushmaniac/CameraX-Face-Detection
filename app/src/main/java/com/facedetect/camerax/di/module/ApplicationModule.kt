package com.ayush.finbox.di.module

import android.content.Context
import com.facedetect.camerax.FinboxApplication
import com.facedetect.camerax.data.ApplicationRepository
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: FinboxApplication) {

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideAppRepository() : ApplicationRepository = ApplicationRepository()
}