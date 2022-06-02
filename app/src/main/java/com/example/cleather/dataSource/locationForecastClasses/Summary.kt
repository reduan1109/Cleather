package com.example.cleather.dataSource.locationForecastClasses

import com.google.gson.annotations.SerializedName


data class Summary (


  @SerializedName("symbol_code"       ) var symbolCode       : String? = null,
  @SerializedName("symbol_confidence" ) var symbolConfidence : String? = null,
  

)