package com.facedetect.camerax.utils.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {

    const val CAMERA_PERMISSION_REQUEST = 1024

    fun requestPermissions(context: Context) {
        if (ContextCompat.checkSelfPermission(context.applicationContext,
                Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        }
    }
}