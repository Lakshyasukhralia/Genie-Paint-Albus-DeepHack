package com.sketchart.sketchart.core.helper

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

fun getImageBody(applicationContext: Application, imageUri: Uri, fileName: String): File {
    val parcelFileDescriptor = applicationContext.contentResolver.openFileDescriptor(
        imageUri,
        "r",
        null
    )
    val file = File(
        applicationContext.cacheDir,
        fileName
    )
    val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
    val bm = getResizedBitmap(inputStream, 768, 1024)
    val outputStream = FileOutputStream(file)
    saveBitmapToOutputStream(bm, outputStream)
//    inputStream.copyTo(outputStream)
    parcelFileDescriptor?.close()
    return file
}

fun saveBitmapToOutputStream(bitmap: Bitmap, outputStream: OutputStream) {
    try {
        // Compress the bitmap and write it to the output stream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun getResizedBitmap(inputStream: InputStream, reqWidth: Int, reqHeight: Int): Bitmap {
    // Read the bytes from the input stream into a ByteArray
    val bytes = inputStream.readBytes()

    // Decode the byte array into a Bitmap
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, this)
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
        inJustDecodeBounds = false
    }

    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}



