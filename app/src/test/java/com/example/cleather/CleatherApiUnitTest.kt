package com.example.cleather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleather.dataSource.LocationForecastApi
import com.example.cleather.dataSource.LocationNameFinderAPi
import com.example.cleather.dataSource.MetAlertsApi
import com.example.cleather.dataSource.locationNameFinderClasses.LocationName
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CleatherApiUnitTest: ViewModel() {
    private val locationNameResponseOslo = "{" +"  \"latitude\": 59.911491," +"  \"longitude\": 10.757933," +"  \"continent\": \"Europe\"," +"  \"lookupSource\": \"coordinates\"," +"  \"continentCode\": \"EU\"," +"  \"localityLanguageRequested\": \"no\"," +"  \"city\": \"Oslo\"," +"  \"countryName\": \"Norway\"," +"  \"postcode\": \"\"," +"  \"countryCode\": \"NO\"," +"  \"principalSubdivision\": \"Oslo\"," +"  \"principalSubdivisionCode\": \"NO-03\"," +"  \"plusCode\": \"9FFGWQ65+H5\"," +"  \"locality\": \"Gamle Oslo\"," +"  \"localityInfo\": {" +"    \"administrative\": [" +"      {" +"        \"name\": \"Norge\"," +"        \"description\": \"country in Northern Europe\"," +"        \"isoName\": \"Norway\"," +"        \"order\": 3," +"        \"adminLevel\": 2," +"        \"isoCode\": \"NO\"," +"        \"wikidataId\": \"Q20\"," +"        \"geonameId\": 3144096" +"      }," +"      {" +"        \"name\": \"Oslo\"," +"        \"description\": \"capital city of Norway\"," +"        \"isoName\": \"Oslo\"," +"        \"order\": 4," +"        \"adminLevel\": 4," +"        \"isoCode\": \"NO-03\"," +"        \"wikidataId\": \"Q585\"," +"        \"geonameId\": 3143244" +"      }," +"      {" +"        \"name\": \"Oslo municipality\"," +"        \"description\": \"municipality in Norway\"," +"        \"order\": 5," +"        \"adminLevel\": 7," +"        \"wikidataId\": \"Q5245991\"," +"        \"geonameId\": 6453366" +"      }," +"      {" +"        \"name\": \"Gamle Oslo\"," +"        \"description\": \"urban district\"," +"        \"order\": 6," +"        \"adminLevel\": 9," +"        \"wikidataId\": \"Q1493178\"," +"        \"geonameId\": 6940966" +"      }" +"    ]," +"    \"informative\": [" +"      {" +"        \"name\": \"Europe\"," +"        \"description\": \"continent on Earth, mainly on the northeastern quadrant, i.e. north-western Eurasia\"," +"        \"order\": 1," +"        \"isoCode\": \"EU\"," +"        \"wikidataId\": \"Q46\"," +"        \"geonameId\": 6255148" +"      }," +"      {" +"        \"name\": \"Scandinavia\"," +"        \"description\": \"region in Northern Europe\"," +"        \"order\": 2," +"        \"wikidataId\": \"Q21195\"," +"        \"geonameId\": 2614165" +"      }" +"    ]" +"  }" +"}"
    private val latt = 59.911491
    private val long = 10.757933

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun locationForecastSuccess() {
        viewModelScope.launch(Dispatchers.Default) {
            assertEquals(((LocationForecastApi.fetchLocationForeCast(latt, long, LocalDate.now(), LocalDate.now().plusDays(3))) != null), true)
        }
    }

    @Test
    fun metAlertSuccess() {
        viewModelScope.launch(Dispatchers.Default) {
            assertEquals(((MetAlertsApi.fetchMetAlert(latt, long)) != null), true)
        }
    }

    @Test
    fun locationFinderSuccessAndCorrectness() {
        viewModelScope.launch(Dispatchers.Default) {
            assertEquals(((LocationNameFinderAPi.fetchLocationName(latt, long)) != null), true)
            assertEquals((LocationNameFinderAPi.fetchLocationName(latt, long)), (Gson().fromJson(locationNameResponseOslo, LocationName::class.java)))
        }
    }
}