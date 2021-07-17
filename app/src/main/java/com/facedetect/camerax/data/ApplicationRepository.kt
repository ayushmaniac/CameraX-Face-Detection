package com.facedetect.camerax.data

import android.content.ContentResolver
import android.graphics.Bitmap
import com.facedetect.camerax.utils.fileutils.CameraXFileManager

class ApplicationRepository {

    fun saveImage(image: Bitmap, contentResolver: ContentResolver) {
        CameraXFileManager.saveImage(bitmap = image, contentResolver)
    }
}