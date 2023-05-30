package org.cazait.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.cazait.model.CafeImage

class CafeImageConverter {
    @TypeConverter
    fun fromCafeImageList(images: List<CafeImage>): String {
        val gson = Gson()
        val json = gson.toJson(images)
        return json
    }

    @TypeConverter
    fun toCafeImageList(json: String): List<CafeImage> {
        val gson = Gson()
        val type = object : TypeToken<List<CafeImage>>() {}.type
        val images = gson.fromJson<List<CafeImage>>(json, type)
        return images
    }
}