package com.facedetect.camerax.ui.main.vm

import android.content.ContentResolver
import android.graphics.Bitmap
import com.facedetect.camerax.data.ApplicationRepository
import com.facedetect.camerax.ui.base.BaseViewModel

class MainViewModel(private val applicationRepository: ApplicationRepository) : BaseViewModel() {
    fun storeImage(bitmap: Bitmap, contentResolver: ContentResolver) {
        applicationRepository.saveImage(bitmap, contentResolver)
    }
}