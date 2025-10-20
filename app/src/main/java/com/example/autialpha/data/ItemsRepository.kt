package com.example.autialpha.data

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

data class Item(val word: String, val imageName: String)

class ItemsRepository {
    fun loadItems(context: Context): List<Item> {
        val list = mutableListOf<Item>()
        val input = runCatching { context.assets.open("items.csv") }.getOrNull()
            ?: return listOf(Item("собака", "dog"))
        BufferedReader(InputStreamReader(input, Charsets.UTF_8)).use { br ->
            br.lineSequence().forEach { line ->
                val trimmed = line.trim()
                if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEach
                val parts = trimmed.split(",")
                if (parts.size >= 2) {
                    list.add(Item(parts[0].trim(), parts[1].trim()))
                }
            }
        }
        return if (list.isNotEmpty()) list else listOf(Item("собака", "dog"))
    }
}


