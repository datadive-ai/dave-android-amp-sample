package ai.datadive.android.sample.kt

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun getProductMenu(context: Context): List<Category> {
    var jsonFileString = getJsonDataFromAsset(context, "item.json")
    val gson = Gson()
    val listCategoryType = object : TypeToken<List<Category>>() {}.type
    var categories: List<Category> = gson.fromJson(jsonFileString, listCategoryType)
    return categories
}
