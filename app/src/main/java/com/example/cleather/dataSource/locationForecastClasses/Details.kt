package com.example.cleather.dataSource.locationForecastClasses

import com.google.gson.annotations.SerializedName


data class Details (

  @SerializedName("air_pressure_at_sea_level"     ) var airPressureAtSeaLevel      : Double? = null,
  @SerializedName("air_temperature"               ) var airTemperature             : Double? = null,
  @SerializedName("air_temperature_percentile_10" ) var airTemperaturePercentile10 : Double? = null,
  @SerializedName("air_temperature_percentile_90" ) var airTemperaturePercentile90 : Double? = null,
  @SerializedName("cloud_area_fraction"           ) var cloudAreaFraction          : Double? = null,
  @SerializedName("cloud_area_fraction_high"      ) var cloudAreaFractionHigh      : Double?    = null,
  @SerializedName("cloud_area_fraction_low"       ) var cloudAreaFractionLow       : Double? = null,
  @SerializedName("cloud_area_fraction_medium"    ) var cloudAreaFractionMedium    : Double? = null,
  @SerializedName("dew_point_temperature"         ) var dewPointTemperature        : Double? = null,
  @SerializedName("fog_area_fraction"             ) var fogAreaFraction            : Double?    = null,
  @SerializedName("relative_humidity"             ) var relativeHumidity           : Double?    = null,
  @SerializedName("ultraviolet_index_clear_sky"   ) var ultravioletIndexClearSky   : Double? = null,
  @SerializedName("wind_from_direction"           ) var windFromDirection          : Double?    = null,
  @SerializedName("wind_speed"                    ) var windSpeed                  : Double? = null,
  @SerializedName("wind_speed_of_gust"            ) var windSpeedOfGust            : Double? = null,
  @SerializedName("wind_speed_percentile_10"      ) var windSpeedPercentile10      : Double?    = null,
  @SerializedName("wind_speed_percentile_90"      ) var windSpeedPercentile90      : Double? = null,


  @SerializedName("probability_of_precipitation" ) var probabilityOfPrecipitation : Double? = null,

  @SerializedName("precipitation_amount"         ) var precipitationAmount        : Double?    = null,
  @SerializedName("precipitation_amount_max"     ) var precipitationAmountMax     : Double?    = null,
  @SerializedName("precipitation_amount_min"     ) var precipitationAmountMin     : Double?    = null,
  @SerializedName("probability_of_thunder"       ) var probabilityOfThunder       : Double? = null,

  @SerializedName("air_temperature_max"          ) var airTemperatureMax          : Double? = null,
  @SerializedName("air_temperature_min"          ) var airTemperatureMin          : Double? = null,



)