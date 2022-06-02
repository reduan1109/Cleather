package com.example.cleather.ui.confirm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleather.dataClasses.DayObject
import com.example.cleather.dataClasses.TripInfo
import com.example.cleather.dataClasses.TripItem
import com.example.cleather.dataSource.ClothesApi
import com.example.cleather.dataSource.LocationForecastApi
import com.example.cleather.dataSource.LocationNameFinderAPi
import com.example.cleather.dataSource.MetAlertsApi
import com.example.cleather.dataSource.locationForecastClasses.DayForecast
import com.example.cleather.dataSource.locationNameFinderClasses.LocationName
import com.example.cleather.enumClasses.ItemBodyPart
import com.example.cleather.enumClasses.ItemSpecialFeature
import com.example.cleather.enumClasses.ItemType
import com.example.cleather.enumClasses.ItemWarmness
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.prof.rssparser.Channel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class ConfirmFragmentViewModel: ViewModel() {

    private var longitudeLatitude: Pair<Double, Double>? = null
    private var dates: Pair<LocalDateTime, LocalDateTime>? = null

    private val weatherList = MutableLiveData<MutableList<DayForecast>>()
    private val rssAlert = MutableLiveData<Channel?>()

    private var locName = MutableLiveData<LocationName?>()

    fun setTripDates(dates: Pair<LocalDateTime, LocalDateTime>){
        this.dates = dates
    }

    fun setTripCoordinates(coordinates: Pair<Double, Double>){
        longitudeLatitude = coordinates
    }

    fun getTripCoordinates(): Pair<Double, Double>?{
        return longitudeLatitude
    }

    fun fetchLocationName(latitude: Double, longitude: Double){
        viewModelScope.launch(Dispatchers.IO){
            LocationNameFinderAPi.fetchLocationName(latitude, longitude)
                .also { locName.postValue(it) }
        }
    }

    fun getLocationName(): MutableLiveData<LocationName?>{
        return locName
    }

    fun fetchWeatherData(startDate: LocalDate, endDate: LocalDate){
        viewModelScope.launch(Dispatchers.IO){
            LocationForecastApi.fetchLocationForeCast(longitudeLatitude!!.first, longitudeLatitude!!.second, startDate, endDate)
                .also { weatherList.postValue(it!!.toMutableList()) }
        }
    }

    fun fetchAlertData(lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO){
            MetAlertsApi.fetchMetAlert(long, lat)
                .also { rssAlert.postValue(it) }
        }
    }

    fun dateToLocalDateTime(selectedDate:Long): LocalDateTime{

        val getYear = SimpleDateFormat("yyyy", Locale.getDefault())
        val getMonth = SimpleDateFormat("MM", Locale.getDefault())
        val getDay = SimpleDateFormat("dd", Locale.getDefault())

        //return formatDate.format(Date(selectedDate))
        val year = getYear.format(Date(selectedDate)).toInt()
        val month = getMonth.format(Date(selectedDate)).toInt()
        val day = getDay.format(Date(selectedDate)).toInt()

        return LocalDateTime.of(year, month, day, 0, 0, 0)
    }


    private fun getTempRange(weatherToday: DayForecast): MutableList<ItemWarmness>{

        val tempRanges = mutableListOf<ItemWarmness>()

        //Set temperature range
        if(weatherToday.getAvgTemp()!! < -5){
            tempRanges.add(ItemWarmness.WARM)
        }

        if(weatherToday.getAvgTemp()!! > -5L && weatherToday.getAvgTemp()!! < 5){
            tempRanges.add(ItemWarmness.TEPIDWARM)
        }

        if(weatherToday.getAvgTemp()!! > 5 && weatherToday.getAvgTemp()!! < 15){
            tempRanges.add(ItemWarmness.TEPID)
        }

        if(weatherToday.getAvgTemp()!! > 15 && weatherToday.getAvgTemp()!! < 20){
            tempRanges.add(ItemWarmness.TEPIDCOLD)
        }

        if(weatherToday.getAvgTemp()!! > 20){
            tempRanges.add(ItemWarmness.COLD)
        }

        return tempRanges
    }

    private fun clothingHasAllFeatures(clothingFeatures : List<ItemSpecialFeature>, requiredFeatures : MutableList<ItemSpecialFeature>): Boolean{
        val featureAmount = requiredFeatures.size
        var foundFeatures = 0

        for(clothingFeature in clothingFeatures){
            if(clothingFeature in requiredFeatures){
                foundFeatures++
            }
        }

        if(foundFeatures == featureAmount){
            return true
        }
        return false
    }

    private fun generateTripItems(weatherToday: DayForecast): MutableList<TripItem> {

        //Hard coded list of default clothes
        val allClothes = ClothesApi.getClothes()

        val chosenClothes = mutableListOf<TripItem>()
        val tempRanges = getTempRange(weatherToday)
        val clothingBodyPart = mutableListOf<ItemBodyPart>()
        val allItemTypes = mutableListOf<ItemType>()
        val neededFeatures = mutableListOf<ItemSpecialFeature>()

        //set all body parts
        clothingBodyPart.add(ItemBodyPart.ARMS)
        clothingBodyPart.add(ItemBodyPart.FACE)
        clothingBodyPart.add(ItemBodyPart.FEET)
        clothingBodyPart.add(ItemBodyPart.HANDS)
        clothingBodyPart.add(ItemBodyPart.NECK)
        clothingBodyPart.add(ItemBodyPart.LOWER_BODY)
        clothingBodyPart.add(ItemBodyPart.UPPER_BODY)
        clothingBodyPart.add(ItemBodyPart.NA)

        //set all clothing types
        allItemTypes.add(ItemType.CLOTHING_UNDERWEAR)
        allItemTypes.add(ItemType.CLOTHING)
        allItemTypes.add(ItemType.CLOTHING_SHELL)
        allItemTypes.add(ItemType.ACCESSORIES)

        //Special features will be added to the shell-clothing
        if(weatherToday.getAvgUv() != null){
            //UV is a special feature if
            if(weatherToday.getAvgUv()!!.compareTo(2.0) > 0){
                neededFeatures.add(ItemSpecialFeature.UV_PROTECTANT)
            }
        }

        if(weatherToday.getMaxWindSpeed() != null){
            if(weatherToday.getMaxWindSpeed()!!.compareTo(10.0) > 0){
                neededFeatures.add(ItemSpecialFeature.WINDPROOF)
            }
        }

        //25% chance of rain?
        if(weatherToday.getProbabilityOfPrecipitation() != null){
            if(weatherToday.getProbabilityOfPrecipitation()!!.compareTo(25.0) > 0){
                neededFeatures.add(ItemSpecialFeature.WATERPROOF)
            }
        }

        for(bodyPart in clothingBodyPart) {
            for(clothingType in allItemTypes){
                var pickedItem: TripItem? = null

                if(clothingType == ItemType.ACCESSORIES && ItemSpecialFeature.UV_PROTECTANT in neededFeatures){
                    //Add all accessories for UV protection
                    val uvFeature = mutableListOf<ItemSpecialFeature>()
                    uvFeature.add(ItemSpecialFeature.UV_PROTECTANT)
                    val uvProtection = allClothes.filter {
                        it.itemBodyPart == bodyPart &&
                                it.itemType == clothingType && clothingHasAllFeatures(it.itemSpecialFeature, uvFeature)
                    }

                    for(uvItem in uvProtection){
                        chosenClothes.add(uvItem)
                    }

                    continue
                }

                for(temp in tempRanges){

                    var clothingPick = allClothes.filter {
                        it.itemWarmness == temp &&
                                it.itemBodyPart == bodyPart &&
                                it.itemType == clothingType
                    }

                    if(clothingPick.isNotEmpty()){
                        pickedItem = clothingPick[0]
                    }

                    clothingPick = allClothes.filter {
                        it.itemWarmness == temp &&
                                it.itemBodyPart == bodyPart &&
                                it.itemType == clothingType &&
                                clothingHasAllFeatures(it.itemSpecialFeature, neededFeatures)
                    }

                    if(clothingPick.isNotEmpty()){
                        //If item has desirable special feature, pic it over other clothing
                        pickedItem = clothingPick[0]
                    }
                }

                if(neededFeatures.size != 0 && ItemSpecialFeature.UV_PROTECTANT !in neededFeatures){
                    if(clothingType == ItemType.CLOTHING_SHELL){
                        //Check feature
                        val clothingPick = allClothes.filter {
                            it.itemBodyPart == bodyPart &&
                                    it.itemType == clothingType &&
                                    clothingHasAllFeatures(it.itemSpecialFeature, neededFeatures)
                        }

                        if(clothingPick.isNotEmpty()){
                            //If item has desirable special feature, pic it over other clothing
                            //chosenClothes.add(pickedItem!!)
                            pickedItem = clothingPick[0]
                        }
                    }
                }

                if(pickedItem != null){
                    chosenClothes.add(pickedItem)
                }
            }
        }

        return chosenClothes
    }

    //RETURNS NULL IF SOMETHING FAILS
    fun generateTrip(name: String):TripInfo?{

        val allDays = mutableListOf<DayObject>()
        val forecastList = weatherList.value ?: return null

        val tripClothesSet = mutableSetOf<TripItem>()

        for (day in forecastList) {
            val tripClothes = generateTripItems(day)
            tripClothesSet.addAll(generateTripItems(day))
            val day = DayObject(day, tripClothes)
            allDays.add(day)
        }

        val staticMapQuery = "https://maps.geoapify.com/v1/staticmap?" +
                "style=klokantech-basic&" +
                "width=2000&" +
                "height=1000&" +
                "center=lonlat:${longitudeLatitude!!.second},${longitudeLatitude!!.first}&" +
                "zoom=14" +
                "&apiKey=b2e1817bb97a450e9efca985fd333ab0"

        return TripInfo(
            staticMapQuery,
            name,
            dates!!.first,
            dates!!.second,
            longitudeLatitude!!,
            rssAlert.value,
            allDays,
            tripClothesSet,
        )
    }
}
