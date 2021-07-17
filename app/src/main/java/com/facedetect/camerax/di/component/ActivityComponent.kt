package com.facedetect.camerax.di.component

import com.facedetect.camerax.di.module.ActivityModule
import com.facedetect.camerax.di.scope.ActivityScope
import com.facedetect.camerax.ui.main.ui.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}