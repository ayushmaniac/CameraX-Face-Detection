package com.facedetect.camerax.ui.main.face_detection

import android.graphics.Bitmap
import android.graphics.Rect
import android.media.Image
import android.util.Log
import com.ayush.finbox.ui.main.face_detection.FaceContourGraphic
import com.ayush.finbox.utils.viewutils.ImageUtils.toBitmap
import com.facedetect.camerax.ui.main.manager.ImageManager
import com.facedetect.camerax.utils.GraphicOverlay
import com.facedetect.camerax.utils.analyzer.BaseImageAnalyzer
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.IOException

class FaceContourDetectionProcessor(private val view: GraphicOverlay) :
    BaseImageAnalyzer<List<Face>>() {

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)

    override val graphicOverlay: GraphicOverlay
        get() = view

    override fun detectInImage(image: InputImage): Task<List<Face>> {
        return detector.process(image)
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Face Detector: $e")
        }
    }



    private fun saveImage(image: Bitmap) {
        ImageManager.imageMutableLiveData.postValue(image)
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Face Detector failed.$e")
    }

    companion object {
        private const val TAG = "FaceDetectorProcessor"
    }

    override fun onSuccess(
        results: List<Face>,
        graphicOverlay: GraphicOverlay,
        rect: Rect,
        mediaImage: Image
    ) {
        graphicOverlay.clear()
        results.forEach {
            val faceGraphic = FaceContourGraphic(graphicOverlay, it, rect)
            graphicOverlay.add(faceGraphic)
        }
        val bitmapData = mediaImage.toBitmap()
        ImageManager.imageMutableLiveData.postValue(bitmapData)
        graphicOverlay.postInvalidate()
    }

}