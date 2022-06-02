package com.example.cleather.dataSource.locationNameFinderClasses

import com.google.gson.annotations.SerializedName


data class LocalityInfo (

  @SerializedName("administrative" ) var administrative : ArrayList<Administrative> = arrayListOf(),
  @SerializedName("informative"    ) var informative    : ArrayList<Informative>    = arrayListOf()

)