package com.example.cleather.dataClasses
import com.prof.rssparser.Channel
import java.time.LocalDateTime

//TripInfo saves information about the trip. The values are more or less self explanatory.
data class TripInfo (
    var tripImage: String,
    var tripName: String,
    var tripStartDate: LocalDateTime,
    var tripEndDate: LocalDateTime,
    var tripLongitudeLatitude: Pair<Double, Double>, //A tuple containing doubles to represent the coordinates in Decimal degrees (DD). Open for change.
    var tripRssAlert: Channel?,
    var dayObjects: MutableList<DayObject>, //This will contain a list of day objects and their weather and clothing.
    var allClothes: MutableSet<TripItem>
) : Comparable<TripInfo> {
    override operator fun compareTo(other: TripInfo): Int {
         if (this.tripLongitudeLatitude == other.tripLongitudeLatitude &&
                 this.tripStartDate ==  other.tripStartDate &&
                 this.tripEndDate == other.tripEndDate)
                     return 0
        return -1
    }
}
