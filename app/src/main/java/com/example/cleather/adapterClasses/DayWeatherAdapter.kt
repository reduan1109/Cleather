package com.example.cleather.adapterClasses

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleather.R
import com.example.cleather.dataClasses.DayObject
import com.example.cleather.databinding.CardForecastSummaryBinding
import java.time.format.DateTimeFormatter

class DayWeatherAdapter(private val dataSet: MutableList<DayObject>): RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>() {
    class ViewHolder(binding: CardForecastSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        var cardTripName    : TextView = binding.cardTripName
        var cardTripDate    : TextView = binding.cardTripDate
        var cardMainTemp    : TextView = binding.cardMainTemp
        var cardHighTemp    : TextView = binding.cardHighTemp
        var cardLowTemp     : TextView = binding.cardLowTemp
        var cardWeatherIconRecycler    : RecyclerView = binding.cardWeatherIconRecycler
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardForecastSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Gets an array of the names of the days
        val weekdays = holder.itemView.resources.getStringArray(R.array.weekDays)

        //Actually setts the aforementioned names
        holder.cardTripName.text =
            if (dataSet[position].forecast?.date?.dayOfWeek?.value != null)
                weekdays[dataSet[position].forecast?.date?.dayOfWeek?.value!! - 1]
            else ""

        //Setts the date of that day
        holder.cardTripDate.text = holder
            .itemView
            .resources
            .getString(R.string.date_format,
                dataSet[position].forecast?.date?.format(DateTimeFormatter.ofPattern("dd.MM"))
                    ?: "error"
            )

        //Setts the avgtmp of that day
        holder.cardMainTemp.text =  holder.itemView.resources.getString(R.string.tempMainString, dataSet[position].forecast?.getAvgTemp())

        //Setts the hightmp of that day
        holder.cardHighTemp.text = holder.itemView.resources.getString(R.string.tempHighString, dataSet[position].forecast?.getMaxTemp())

        //Setts the lowtmp of that day
        holder.cardLowTemp.text = holder.itemView.resources.getString(R.string.tempLowString, dataSet[position].forecast?.getMinTemp())

        //Displays the icon summaries
        if ( dataSet[position].forecast?.getSymbolCodesList() != null) {
            val symbols = mutableListOf<Int>()

            dataSet[position].forecast?.getSymbolCodesList()!!.forEach { symbol ->
                symbols.add(
                    holder.itemView.resources.getIdentifier(
                        symbol, "drawable", holder.itemView.context.packageName
                    )
                )
            }
            holder.cardWeatherIconRecycler.adapter = WeatherIconAdapter(symbols)
        }
    }

    override fun getItemCount() = dataSet.size
}