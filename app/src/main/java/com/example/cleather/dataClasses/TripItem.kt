package com.example.cleather.dataClasses
import com.example.cleather.enumClasses.ItemBodyPart
import com.example.cleather.enumClasses.ItemSpecialFeature
import com.example.cleather.enumClasses.ItemType
import com.example.cleather.enumClasses.ItemWarmness

//TripItem saves the information about a singular item. There are a few nuances on how the item is represented, se comments.
//Feel free to add an attribute if you fell it needs it
data class TripItem (
    var isChecked: Boolean = false, //Is it checked in trip?
    var itemName: String, //Name of item, ex.
    var itemGraphic: String, //Should be drawable
    val itemType: ItemType, //Enum class describing what type of item it is.
    var itemWarmness: ItemWarmness, //Should be NA if itemType makes this not applicable. Ex. sunscreen is of type consumable and does not need a warmness value.
    var itemBodyPart: ItemBodyPart, //Should be NA if itemType is not something you put on your body. Ex. ski lubrication is not an item that you put on your body.
    var itemSpecialFeature: List<ItemSpecialFeature>,
    var itemID: Int //Added this so that it might be easier to get the specific item from database / Check if item is already added to a trip
)