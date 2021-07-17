package com.facedetect.camerax.ui.main.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facedetect.camerax.R
import com.facedetect.camerax.di.component.ActivityComponent
import com.facedetect.camerax.ui.base.BaseActivity
import com.facedetect.camerax.ui.main.manager.CameraXManager
import com.facedetect.camerax.ui.main.manager.ImageManager
import com.facedetect.camerax.ui.main.vm.MainViewModel
import com.facedetect.camerax.utils.permission.PermissionHelper
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>(), View.OnClickListener{

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @Inject
    lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    lateinit var cameraManager: CameraXManager



    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        ibSwitchCamera.setOnClickListener(this)
        initCameraXManager()
        checkForPermission()
        observeImages()
    }


//To test
/*
    private fun clickPictureObserver() {
        ImageUtils.faceDetectMutableLiveData.observe(this, {
            cameraManager.capturePicture().takePicture(mainExecutor, object : OnImageCapturedCallback(){
                @SuppressLint("UnsafeOptInUsageError")
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    viewModel.storeImage(image.image!!.toBitmap(), contentResolver)
                }
                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                }
            })
        })
    }
*/

    private fun observeImages() {
        ImageManager.imageMutableLiveData.observe(this, {
            storeImage(it)
        })
    }

    private fun storeImage(bitmap: Bitmap) {
        if(cameraManager.cameraSelectorOption == CameraSelector.LENS_FACING_FRONT){
            viewModel.storeImage(bitmap, contentResolver)
        }
    }

    private fun checkForPermission() {
        if (allPermissionsGranted()) {
            cameraManager.startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun initCameraXManager() {
        cameraManager = CameraXManager(
            this,
            previewView,
            this,
            graphicOverlay
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                cameraManager.startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ibSwitchCamera -> {
                cameraManager.changeCameraSelector()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


}
