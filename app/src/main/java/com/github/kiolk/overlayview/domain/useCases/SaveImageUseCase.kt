package com.github.kiolk.overlayview.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import logcat.asLog
import logcat.logcat
import java.io.File
import java.io.FileOutputStream

interface SaveImageUseCase {

    suspend operator fun invoke(url: String): File?
}

class SaveImageUseCaseImpl(private val context: Context) : SaveImageUseCase {
    override suspend fun invoke(url: String): File? {
        try {
            val futureTarget = Glide.with(context)
                .asBitmap()
                .load(url)
                .submit()
            val bitmap = futureTarget.get()
            val file =
                File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), DEFAULT_FILE_NAME)
            withContext(Dispatchers.IO) {
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        QUALITY,
                        outputStream
                    )
                }
            }
            return file
        } catch (exception: Exception) {
            logcat { exception.asLog() }
            return null
        }
    }

    companion object {
        const val DEFAULT_FILE_NAME = "image.png"
        const val QUALITY = 100
    }
}