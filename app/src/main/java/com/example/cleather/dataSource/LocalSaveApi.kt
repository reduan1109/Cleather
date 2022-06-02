package com.example.cleather.dataSource

import android.content.Context
import android.util.Log
import com.example.cleather.dataClasses.TripInfo
import com.example.cleather.dataClasses.TripItem
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.*
import java.time.LocalDateTime


/*
* UNTESTED AND UNUSED. BACKUP ONLY*/
class LocalSaveApi {
    companion object {
        /*internal class LocalDateTypeAdapter : TypeAdapter<LocalDate>() {

            override fun write(out: JsonWriter, value: LocalDate) {
                out.value(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
            }

            override fun read(input: JsonReader): LocalDate = LocalDate.parse(input.nextString())
        }*/

        private class LocalDateTypeAdapter : TypeAdapter<LocalDateTime>() {
            @Throws(IOException::class)
            override fun write(jsonWriter: JsonWriter, localDate: LocalDateTime) {
                jsonWriter.value(localDate.toString())
            }

            @Throws(IOException::class)
            override fun read(jsonReader: JsonReader): LocalDateTime {
                return LocalDateTime.parse(jsonReader.nextString())
            }
        }

        private const val TRIP_FILE_NAME = "trips.json"
        private const val ITEMS_FILE_NAME = "clothes.json"

        fun saveTrips(listOfTrips: List<TripInfo>, context: Context): Boolean {
            val builder = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTypeAdapter().nullSafe())

            val data = builder.create().toJson(listOfTrips)

            return try {
                val outputStreamWriter = OutputStreamWriter(context.openFileOutput(TRIP_FILE_NAME, Context.MODE_PRIVATE))
                outputStreamWriter.write(data)
                outputStreamWriter.close()
                true
            } catch (e: IOException) {
                Log.e("Exception", "File write failed: $e")
                false
            }
        }

        fun readTrips(context: Context): List<TripInfo>? {
            var data = ""

            try {
                val inputStream: InputStream? = context.openFileInput(TRIP_FILE_NAME)

                if (inputStream != null) {
                    val inputStreamReader = InputStreamReader(inputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)
                    var receiveString: String? = ""
                    val stringBuilder = StringBuilder()

                    while (bufferedReader.readLine().also { receiveString = it } != null) {
                                stringBuilder.append("\n").append(receiveString)
                    }

                    inputStream.close()
                    data = stringBuilder.toString()
                }
            } catch (e: FileNotFoundException) {
                Log.e("readTrips", "File not found: $e")
            } catch (e: IOException) {
                Log.e("readTrips", "Can not read file: $e")
            }

            val builder = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTypeAdapter().nullSafe())


            return if(data.isEmpty()) null else builder.create().fromJson(data, Array<TripInfo>::class.java).toList()
        }

        fun saveItems(listOfTrips: List<TripItem>, context: Context): Boolean {
            val data = Gson().toJson(listOfTrips)

            return try {
                val outputStreamWriter = OutputStreamWriter(context.openFileOutput(ITEMS_FILE_NAME, Context.MODE_PRIVATE))
                outputStreamWriter.write(data)
                outputStreamWriter.close()
                true
            } catch (e: IOException) {
                Log.e("Exception", "File write failed: $e")
                false
            }
        }

        fun readItems(context: Context): List<TripItem>? {
            var data = ""

            try {
                val inputStream: InputStream? = context.openFileInput(ITEMS_FILE_NAME)

                if (inputStream != null) {
                    val inputStreamReader = InputStreamReader(inputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)
                    var receiveString: String? = ""
                    val stringBuilder = StringBuilder()

                    while (bufferedReader.readLine().also { receiveString = it } != null) {
                        stringBuilder.append("\n").append(receiveString)
                    }

                    inputStream.close()
                    data = stringBuilder.toString()
                }
            } catch (e: FileNotFoundException) {
                Log.e("login activity", "File not found: $e")
            } catch (e: IOException) {
                Log.e("login activity", "Can not read file: $e")
            }

            return if(data.isEmpty()) null else Gson().fromJson(data, Array<TripItem>::class.java).toList()
        }
    }
}