package com.facedetect.camerax.utils.fileutils

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.IOException

object CameraXFileManager {

    fun saveImage(bitmap: Bitmap, contentResolver: ContentResolver): Boolean {
        val name = "ayush"
        val imageLoc = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        
        val collectionValue = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg")
            put(MediaStore.Images.Media.WIDTH,bitmap.width)
            put(MediaStore.Images.Media.HEIGHT,bitmap.height)
        }
        return try {
            contentResolver.insert(imageLoc,collectionValue)?.also {uri->
                contentResolver.openOutputStream(uri).use {
                    if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)){
                        throw IOException("Can't save Image")
                    }
                }
            } ?: throw IOException("Can't create MediaStore")
            true
        }
        catch (e : IOException){
            e.printStackTrace()
            false
        }
    }
}