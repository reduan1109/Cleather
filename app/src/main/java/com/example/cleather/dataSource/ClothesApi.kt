package com.example.cleather.dataSource

import com.example.cleather.dataClasses.TripItem
import com.example.cleather.dataSource.locationForecastClasses.DayForecast
import com.example.cleather.enumClasses.ItemBodyPart
import com.example.cleather.enumClasses.ItemSpecialFeature
import com.example.cleather.enumClasses.ItemType
import com.example.cleather.enumClasses.ItemWarmness

/*
* Class is supposed to do generation and getting of clothes.*/
class ClothesApi {
    companion object {
        //Returns a list of clothes.
        //TODO: Is quickfix until the database is finished
        fun getClothes(): List<TripItem> {
            return listOf(
                TripItem(
                    false,
                    "t-shorte",
                    "tshirt1",
                    ItemType.CLOTHING_UNDERWEAR,
                    ItemWarmness.COLD,
                    ItemBodyPart.UPPER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    0
                ),
                TripItem(
                    false,
                    "Ull undertøy top",
                    "checked",
                    ItemType.CLOTHING_UNDERWEAR,
                    ItemWarmness.COLD,
                    ItemBodyPart.UPPER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    1
                ),
                TripItem(
                    false,
                    "genser",
                    "jumper",
                    ItemType.CLOTHING,
                    ItemWarmness.TEPID,
                    ItemBodyPart.UPPER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    2
                ),
                TripItem(
                    false,
                    "jakke",
                    "checked",
                    ItemType.CLOTHING_SHELL,
                    ItemWarmness.WARM,
                    ItemBodyPart.UPPER_BODY,
                    listOf(ItemSpecialFeature.WATERPROOF, ItemSpecialFeature.WINDPROOF),
                    3
                ),
                TripItem(
                    false,
                    "underbokser",
                    "checked",
                    ItemType.CLOTHING_UNDERWEAR,
                    ItemWarmness.COLD,
                    ItemBodyPart.LOWER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    4
                ),
                TripItem(
                    false,
                    "bukse",
                    "trousers",
                    ItemType.CLOTHING,
                    ItemWarmness.WARM,
                    ItemBodyPart.UPPER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    5
                ),
                TripItem(
                    false,
                    "solbriller",
                    "sunglasses",
                    ItemType.ACCESSORIES,
                    ItemWarmness.COLD,
                    ItemBodyPart.FACE,
                    listOf(ItemSpecialFeature.UV_PROTECTANT),
                    6
                ),
                TripItem(
                    false,
                    "hettegenser",
                    "hoodie1",
                    ItemType.CLOTHING,
                    ItemWarmness.TEPIDWARM,
                    ItemBodyPart.UPPER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    7
                ),
                TripItem(
                    false,
                    "bukse",
                    "trousers",
                    ItemType.CLOTHING,
                    ItemWarmness.TEPID,
                    ItemBodyPart.LOWER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    8
                ),
                TripItem(
                    false,
                    "varmere bukse",
                    "trousers",
                    ItemType.CLOTHING,
                    ItemWarmness.TEPIDWARM,
                    ItemBodyPart.LOWER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    9
                ),
                TripItem(
                    false,
                    "varmere shorts",
                    "shorts2",
                    ItemType.CLOTHING,
                    ItemWarmness.TEPIDCOLD,
                    ItemBodyPart.LOWER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    10
                ),
                TripItem(
                    false,
                    "varmest bukse",
                    "trousers",
                    ItemType.CLOTHING,
                    ItemWarmness.WARM,
                    ItemBodyPart.LOWER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    11
                ),
                TripItem(
                    false,
                    "shorts",
                    "shorts1",
                    ItemType.CLOTHING,
                    ItemWarmness.COLD,
                    ItemBodyPart.LOWER_BODY,
                    listOf(ItemSpecialFeature.NA),
                    12
                ),
                TripItem(
                    false,
                    "sol hat",
                    "sunhat",
                    ItemType.ACCESSORIES,
                    ItemWarmness.NA,
                    ItemBodyPart.FACE,
                    listOf(ItemSpecialFeature.UV_PROTECTANT),
                    13
                )
            )
        }

        //Clothes picking algorithm. Has a lot of arguments for customization. Unused atm
        fun generatePackList(forecastOfDay: DayForecast,   //The forecast that is in question
                             avgBias: Boolean = true,      //Allows for a more dynamic algorithm. Ex. wants to look at avg tmp when deciding
                             lowBias: Boolean = false,     // Wants to look at min tmp when deciding
                             highBias: Boolean = false,    // Wants to look at max tmp when deciding
                             rainProbabilityLimit: Double = 20.0, //The limit at which an item with waterproofness will be allways be reccomended
                             windSpeedLimit: Double = 2.0, //The limit at which a windbrekaer will always be recommended
                             ultraVioletLimit: Double = 2.0//The limit at which a sundblocker will always be recommended
        ): List<TripItem>? {
            val outPut = mutableSetOf<TripItem>()

            val tmp: Double = when { //Decides what tmp to be biased
                    avgBias -> forecastOfDay.getAvgTemp() ?: return null
                    highBias -> forecastOfDay.getAvgTemp() ?: return null
                    else -> forecastOfDay.getMinTemp() ?: return null
                }

            val clothesForTrip = this.getClothes()


            val itemsWithRightWarmness =
                clothesForTrip.filter { it.itemWarmness.value()!! > tmp }

            /*
            //Needs a check if water limit has been met
            val itemsWithWaterResistance =
                clothesForTrip.filter { it.itemSpecialFeature.contains(ItemSpecialFeature.WATERPROOF) }

            //Needs a check if wind limit has been met
            val itemsWithWindResistance =
                clothesForTrip.filter { it.itemSpecialFeature.contains(ItemSpecialFeature.WATERPROOF) }

            //Needs a check if ultra violet limit has been met
            val itemsWithUltraVioletResistance =
                clothesForTrip.filter { it.itemSpecialFeature.contains(ItemSpecialFeature.WATERPROOF) }
             */

            return outPut.toList()
        }
    }
}