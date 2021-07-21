package com.serhii.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import org.junit.Assert.assertNotNull

/**
 * Opens a file from test/resources folder, json/ subfolder.
 */

val gson: Gson = Gson()

/**
 * fromJson - function that takes fileName of json (without extension) and class type and
 * returns model of this class converted from json file.
 * If we need to change Json library, this is exact place to do it.
 */
inline fun <reified T> fromJson(fileName: String): T {
    val jsonStr = loadJson<T>(fileName)
    assertNotNull(jsonStr)
    return gson.fromJson(jsonStr, object : TypeToken<T>() {}.type)
}

inline fun <reified T> loadJson(fileName: String): String {
    var classLoader = T::class.java.classLoader
    if (classLoader == null) {
        classLoader =
            requireNotNull(ClassLoader.getSystemClassLoader()) { "Class loader can't be null" }
    }
    val file = File(classLoader.getResource("json/$fileName.json").file)

    val fileInputStream: FileInputStream
    val bFile = ByteArray(file.length().toInt())
    try {
        fileInputStream = FileInputStream(file)
        fileInputStream.read(bFile)
        fileInputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return String(bFile)
}
