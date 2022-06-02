package com.example.cleather.dataSource.locationNameFinderClasses

import com.google.gson.annotations.SerializedName


data class Administrative (

  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("description" ) var description : String? = null,
  @SerializedName("isoName"     ) var isoName     : String? = null,
  @SerializedName("order"       ) var order       : Int?    = null,
  @SerializedName("adminLevel"  ) var adminLevel  : Int?    = null,
  @SerializedName("isoCode"     ) var isoCode     : String? = null,
  @SerializedName("wikidataId"  ) var wikidataId  : String? = null,
  @SerializedName("geonameId"   ) var geonameId   : Int?    = null

)