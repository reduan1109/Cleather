package com.example.cleather.database.data.clothes

import androidx.room.TypeConverter
import com.example.cleather.enumClasses.ItemBodyPart
import com.example.cleather.enumClasses.ItemSpecialFeature
import com.example.cleather.enumClasses.ItemType
import com.example.cleather.enumClasses.ItemWarmness

// different TypeConverters for the enum-classes. Used for storing in database
class Converter {

    // from ItemType to String
    @TypeConverter
    fun fromItemType(itemType : ItemType): String {
        return itemType.name
    }

    // from String to ItemType
    @TypeConverter
    fun toItemType(itemType : String): ItemType {
        return ItemType.valueOf(itemType)
    }

    // from ItemWarmness to String
    @TypeConverter
    fun fromItemWarmness(itemWarmness: ItemWarmness): String {
        return itemWarmness.name
    }

    // from String to ItemWarmness
    @TypeConverter
    fun toItemWarmness(itemWarmness: String): ItemWarmness {
        return ItemWarmness.valueOf(itemWarmness)
    }

    // from ItemBodyPart to String
    @TypeConverter
    fun fromItemBodyPart(itemBodyPart: ItemBodyPart): String {
        return itemBodyPart.name
    }

    // from String to ItemBodyPart
    @TypeConverter
    fun toItemBodyPart(itemBodyPart: String): ItemBodyPart {
        return ItemBodyPart.valueOf(itemBodyPart)
    }

    // from list of ItemSpecialFeature to String
    @TypeConverter
    fun storedStringListLineType(value: String): List<ItemSpecialFeature> {
        val dbValues = value.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
        val enums: MutableList<ItemSpecialFeature> = ArrayList()

        for (s in dbValues)
            enums.add(ItemSpecialFeature.valueOf(s))
        return enums
    }

    // from String to list of ItemSpecialFeature
    @TypeConverter
    fun listLineTypeToStoredString(listItemSpecialFeature: List<ItemSpecialFeature>): String {
        var value = ""

        for (ItemSpecialFeature in listItemSpecialFeature)
            value += ItemSpecialFeature.name + ","

        return value
    }
}