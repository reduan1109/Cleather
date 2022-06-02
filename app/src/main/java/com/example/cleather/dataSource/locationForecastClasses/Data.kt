package com.example.cleather.dataSource.locationForecastClasses

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("instant"       ) var instant     : Instant?     = Instant(),
  @SerializedName("next_12_hours" ) var next12Hours : Next12Hours? = Next12Hours(),
  @SerializedName("next_1_hours"  ) var next1Hours  : Next1Hours?  = Next1Hours(),
  @SerializedName("next_6_hours"  ) var next6Hours  : Next6Hours?  = Next6Hours()

)