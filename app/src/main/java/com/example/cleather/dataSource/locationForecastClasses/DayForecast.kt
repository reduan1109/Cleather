package com.example.cleather.dataSource.locationForecastClasses

import java.time.LocalDate

/*
* This object contains the weather information for a given day and provides an
* interface for the users that might not know how a timeSeries looks like.*/
data class DayForecast (
        var date: LocalDate,
        var timeSeries: MutableList<Timeseries>? = null

) : Comparable<DayForecast> {
        override operator fun compareTo(other: DayForecast): Int { //Comparator compares on date.
                return other.date.compareTo(this.date)
        }

        fun getAvgTemp(): Double? { //Calculates the average temperature
                timeSeries ?: return null

                var sumTmp = 0.0
                var counterTmp = 0

                for (timePoint in timeSeries!!) {
                        if (timePoint.data?.instant?.details?.airTemperature == null) continue

                        sumTmp += (timePoint.data?.instant?.details?.airTemperature!!)
                        counterTmp++
                }

                return sumTmp/counterTmp
        }

        fun getMinTemp(): Double? { //Finds the lowes temperature
                timeSeries ?: return null

                var minTmp: Double? = null
                for (timePoint in timeSeries!!) {
                        timePoint.data?.next6Hours?.details?.airTemperatureMin ?: continue

                        if (minTmp == null){
                                minTmp = timePoint.data?.next6Hours?.details?.airTemperatureMin!!
                                continue
                        }

                        if (minTmp > timePoint.data?.next6Hours?.details?.airTemperatureMin!!.toDouble())
                                minTmp = timePoint.data?.next6Hours?.details?.airTemperatureMin!!
                }

                return minTmp
        }

        fun getMaxTemp(): Double? { //Finds the highest temperature
                timeSeries ?: return null

                var maxTmp: Double? = null
                for (timePoint in timeSeries!!) {
                        if (timePoint.data?.next6Hours?.details?.airTemperatureMax == null) continue

                        if (maxTmp == null){
                                maxTmp = timePoint.data?.next6Hours?.details?.airTemperatureMin!!
                                continue
                        }

                        if (maxTmp < timePoint.data?.next6Hours?.details?.airTemperatureMax!!.toDouble())
                                maxTmp = timePoint.data?.next6Hours?.details?.airTemperatureMax!!
                }

                return maxTmp
        }

        fun getAvgUv(): Double? { //Calculates the average UV
                timeSeries ?: return null

                var sumUv = 0.0
                var counterUv = 0

                for (timePoint in timeSeries!!) {
                        if (timePoint.data?.instant?.details?.ultravioletIndexClearSky == null) continue

                        sumUv += (timePoint.data?.instant?.details?.ultravioletIndexClearSky!!)
                        counterUv++
                }

                return sumUv/counterUv
        }

        fun getMaxWindSpeed(): Double? { //Finds the highest wind speed
                timeSeries ?: return null

                var maxWind = 0.0
                for (timePoint in timeSeries!!) {
                        if (timePoint.data?.instant?.details?.windSpeed == null) continue

                        if (maxWind < timePoint.data?.instant?.details?.windSpeed!!.toDouble())
                                maxWind = timePoint.data?.instant?.details?.windSpeed!!
                }
                return maxWind
        }

        fun getProbabilityOfPrecipitation(): Double? { //Finds the highest probability of Precipitation for that day
                timeSeries ?: return null

                var probabilityPrecipitation = 0.0
                for (timePoint in timeSeries!!) {
                        if (timePoint.data?.next6Hours?.details?.probabilityOfPrecipitation == null) continue

                        if (probabilityPrecipitation < timePoint.data?.next6Hours?.details?.probabilityOfPrecipitation!!.toDouble())
                                probabilityPrecipitation = timePoint.data?.next6Hours?.details?.probabilityOfPrecipitation!!
                }

                return probabilityPrecipitation
        }

        fun getSymbolCodesMap(): MutableMap<String, Int>? { //Returns a frequency map over the symbol summaries. Used to show symbols based on frequency
                timeSeries ?: return null

                val output = mutableMapOf<String, Int>()

                for (timePoint in timeSeries!!) {
                        if(timePoint.data?.next6Hours?.summary?.symbolCode == null) continue

                        if (!output.containsKey(timePoint.data?.next6Hours?.summary?.symbolCode!!))
                                output[timePoint.data?.next6Hours?.summary?.symbolCode!!] = 1
                        else
                                output[timePoint.data?.next6Hours?.summary?.symbolCode!!] =
                                        output[timePoint.data?.next6Hours?.summary?.symbolCode!!]!!.plus(1)
                }

                return output
        }

        fun getSymbolCodesList(distinct: Boolean = false): List<String>? { //Returns a list of symbolCodes. The user can specify whether it returns a distinct list or not.
                timeSeries ?: return null

                val output = mutableListOf<String>()

                for (timePoint in timeSeries!!) {
                        if(timePoint.data?.next6Hours?.summary?.symbolCode == null) continue

                        else output.add(timePoint.data?.next6Hours?.summary?.symbolCode!!)
                }

                return if (output.isEmpty()) null else if (distinct) output.distinct() else output
        }

}