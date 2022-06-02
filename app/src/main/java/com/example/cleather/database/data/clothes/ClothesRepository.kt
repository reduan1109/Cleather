package com.example.cleather.database.data.clothes

import android.content.Context
import com.example.cleather.database.data.CleatherDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClothesRepository(context: Context) {
    var db: ClothesDao = CleatherDatabase.getDatabase(context).clothesDao()

    fun getAllData(): List<Clothes> {
        return db.getAllData()
    }

    // unused, would be used if we made a way for the user to add clothes
    fun addClothes(clothes: Clothes) {
        CoroutineScope(Dispatchers.IO).launch {
            db.addClothes(clothes)
        }
    }

    // unused would be used if we made a way for the user to delete clothes
    fun deleteClothes(clothes: Clothes) {
        CoroutineScope(Dispatchers.IO).launch {
            db.deleteClothes(clothes)
        }
    }
}