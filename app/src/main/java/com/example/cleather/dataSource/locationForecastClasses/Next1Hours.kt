package com.example.cleather.dataSource.locationForecastClasses

import com.google.gson.annotations.SerializedName


data class Next1Hours (

  @SerializedName("summary" ) var summary : Summary? = Summary(),
  @SerializedName("details" ) var details : Details? = Details()

)