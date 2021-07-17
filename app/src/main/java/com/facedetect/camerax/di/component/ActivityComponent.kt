package com.ayush.finbox.di.component

import com.ayush.finbox.di.module.ActivityModule
import com.ayush.finbox.di.scope.ActivityScope
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