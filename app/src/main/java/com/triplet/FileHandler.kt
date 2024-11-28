package com.triplet

import android.content.Context
import java.io.File
import java.io.IOException

class FileHandler {

    fun saveFileToExternalStorage(context: Context, fileName: String, content: String): Boolean {
        return try {

            val file = File(context.getExternalFilesDir(null), fileName)
            file.writeText(content)

            println("Файл сохранён по пути: ${file.absolutePath}")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun deleteFileFromExternalStorage(context: Context, fileName: String): Boolean {
        val file = File(context.getExternalFilesDir(null), fileName)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }


    fun restoreFileFromInternalStorage(context: Context, fileName: String): Boolean {
        return try {
            val internalFile = File(context.filesDir, fileName)
            if (internalFile.exists()) {
                val externalFile = File(context.getExternalFilesDir(null), fileName)
                internalFile.inputStream().copyTo(externalFile.outputStream()) // Переносим файл
                true
            } else {
                false
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun saveFileToInternalStorage(context: Context, fileName: String, content: String): Boolean {
        return try {
            val file = File(context.filesDir, fileName)
            file.writeText(content)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}
