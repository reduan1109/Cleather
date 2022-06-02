package com.example.cleather.dataSource.locationNameFinderClasses

import com.google.gson.annotations.SerializedName


data class LocationName (

  @SerializedName("latitude"                  ) var latitude                  : Double?       = null,
  @SerializedName("longitude"                 ) var longitude                 : Double?       = null,
  @SerializedName("continent"                 ) var continent                 : String?       = null,
  @SerializedName("lookupSource"              ) var lookupSource              : String?       = null,
  @SerializedName("continentCode"             ) var continentCode             : String?       = null,
  @SerializedName("localityLanguageRequested" ) var localityLanguageRequested : String?       = null,
  @SerializedName("city"                      ) var city                      : String?       = null,
  @SerializedName("countryName"               ) var countryName               : String?       = null,
  @SerializedName("postcode"                  ) var postcode                  : String?       = null,
  @SerializedName("countryCode"               ) var countryCode               : String?       = null,
  @SerializedName("principalSubdivision"      ) var principalSubdivision      : String?       = null,
  @SerializedName("principalSubdivisionCode"  ) var principalSubdivisionCode  : String?       = null,
  @SerializedName("plusCode"                  ) var plusCode                  : String?       = null,
  @SerializedName("locality"                  ) var locality                  : String?       = null,
  @SerializedName("localityInfo"              ) var localityInfo              : LocalityInfo? = LocalityInfo()

)