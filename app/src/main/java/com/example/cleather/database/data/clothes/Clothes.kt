package com.example.cleather.database.data.clothes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleather.enumClasses.ItemBodyPart
import com.example.cleather.enumClasses.ItemSpecialFeature
import com.example.cleather.enumClasses.ItemType
import com.example.cleather.enumClasses.ItemWarmness

// The entity class with itemId as primaryKey
@Entity(tableName = "clothes_table")
data class Clothes (
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val isChecked: Boolean,
    val itemName: String,
    val itemGraphic: String,
    val itemType: ItemType,
    val itemWarmness: ItemWarmness,
    val itemBodyPart: ItemBodyPart,
    val itemSpecialFeature: List<ItemSpecialFeature>
)