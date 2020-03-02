package com.chama.challenge.heritages.repository

import android.content.Context
import com.chama.challenge.heritages.model.Heritage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

private const val ITEM_SIZE = 20

class DataProvider(
    private val context: Context,
    gson: Gson
) {

    private val heritages = mutableMapOf<Int, List<Heritage>>()

    init {
        val data = readFromJson()
        val type = object : TypeToken<List<Heritage>>() {}.type
        val result: List<Heritage> = gson.fromJson(data, type) ?: emptyList()

        result.chunked(ITEM_SIZE).forEachIndexed { page, list ->
            heritages[page] = list
        }
    }

    fun getHeritages(page: Int): List<Heritage> = heritages[page].orEmpty()

    private fun readFromJson(): String? = try {
        val inputStream: InputStream = context.assets.open("heritages.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        buffer.toString(Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}
