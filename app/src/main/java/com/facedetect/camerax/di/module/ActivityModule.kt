package com.ayush.finbox.di.module

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.ViewModelProvider
import com.facedetect.camerax.data.ApplicationRepository
import com.facedetect.camerax.ui.base.BaseActivity
import com.facedetect.camerax.ui.main.vm.MainViewModel
import com.facedetect.camerax.utils.viewmodelutils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule (private val activity: BaseActivity<*>) {

    @Provides
    fun provideContext(
    ) : Context = activity


    @Provides
    fun provideCameraProviderFuture() = ProcessCameraProvider.getInstance(activity)

    @Provides
    fun provideMainViewModel(
        applicationRepository: ApplicationRepository
    ): MainViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(applicationRepository)
        }).get(MainViewModel::class.java)
}