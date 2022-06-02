package com.example.cleather.adapterClasses

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cleather.MainActivityViewModel
import com.example.cleather.R
import com.example.cleather.dataClasses.TripInfo
import com.example.cleather.databinding.CardTripSummaryBinding
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter
import java.util.*


class TripSummaryAdapter(private val dataSet: MutableList<TripInfo>): RecyclerView.Adapter<TripSummaryAdapter.ViewHolder>() {
    class ViewHolder(binding: CardTripSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        var cardTripAlert   : ImageView = binding.cardTripAlert
        var cardAlertTitle  : TextView = binding.cardTripAlertTitle
        var cardTripImage   : ImageView = binding.cardTripImage
        var cardTripName    : TextView = binding.cardTripName
        var cardTripDate    : TextView = binding.cardTripDate
        var cardMainTemp    : TextView = binding.cardMainTemp
        var cardHighTemp    : TextView = binding.cardHighTemp
        var cardLowTemp     : TextView = binding.cardLowTemp
        var cardWeatherIconRecycler    : RecyclerView = binding.cardWeatherIconRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardTripSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = dataSet[position] //Ease of use, saves space in editor

        var avgtmp: Double?  = null
        var avgcounter: Int? = null
        var mintmp: Double?  = null
        var maxtmp: Double?  = null
        val tmpSymbols: TreeMap<String, Int> = TreeMap<String, Int>() //TreeMap for easy frequency sort.

        //Calculates averages/gets the values above
        for (dayObject in trip.dayObjects) {
            //Skips if there has been an error while fetching data from API, and forecast for that day is null
            val foreCast = dayObject.forecast ?: continue

            //Adds to avgtmp (will get divided later to calc avg)
            if (foreCast.getAvgTemp() != null && avgtmp == null) {
                avgcounter = 1
                avgtmp = foreCast.getAvgTemp()
            } else {
                avgcounter = avgcounter!! + 1
                avgtmp = avgtmp!! + foreCast.getAvgTemp()!!
            }

            //If a mintmp hasn't been set, i.e. a temp that isn't null hasn't been found, then set it.
            //Important because mintmp is of nullable type
            if (foreCast.getMinTemp() != null && mintmp == null)
                mintmp = foreCast.getMinTemp()
            //Checks if previous is more than current, and setts current if true.
            if (foreCast.getMinTemp() != null)
                //Is asserted as non-null because of previous checks
                if (mintmp!! > foreCast.getMinTemp()!!)
                    mintmp = foreCast.getMinTemp()!!

            //Same as mintmp except it's for maxtmp.
            if (foreCast.getMaxTemp() != null && maxtmp == null)
                maxtmp = foreCast.getMaxTemp()
            if (foreCast.getMaxTemp() != null)
                if (maxtmp != null && maxtmp < foreCast.getMaxTemp()!!)
                    maxtmp = foreCast.getMaxTemp()!!

            //Puts all symbols in tree
            if (foreCast.getSymbolCodesMap() != null) tmpSymbols.putAll(foreCast.getSymbolCodesMap()!!)
        }

        //Gets the identifiers for the symbolcodes. Most frequent first
        val symbols: MutableList<Int> = mutableListOf()
        for (symbol in tmpSymbols.keys)
            symbols.add(
                holder.itemView.resources.getIdentifier(
                    symbol, "drawable", holder.itemView.context.packageName
                )
            )

        //Draw alert icon, if alert is reported
        Log.e("RSS", trip.tripRssAlert?.articles.toString()) //TODO: Remember to remove
        if (trip.tripRssAlert?.articles != null)
            if (trip.tripRssAlert?.articles!!.isNotEmpty()) {
                holder.cardTripAlert.setImageResource (
                    holder.itemView.resources.getIdentifier (
                        "yellowdanger",
                        "drawable",
                        holder.itemView.context.packageName
                    )
                )
            } else
                holder.cardAlertTitle.text = ""

        //Draw tripImage into card
        Picasso.get().load(trip.tripImage).fit().centerCrop().into(holder.cardTripImage)

        //Draw tripName into card
        holder.cardTripName.text = trip.tripName

        //Draw Date into card
        holder.cardTripDate.text = holder
            .itemView
            .resources
            .getString(R.string.date_range_format,
                trip.tripStartDate.format(DateTimeFormatter.ofPattern("dd.MM")),
                trip.tripEndDate.format(DateTimeFormatter.ofPattern("dd.MM"))
            )

        //Draw tripAvgTmp into card
        if (avgtmp != null) {
            holder.cardMainTemp.text = holder.itemView.resources.getString(R.string.tempMainString, (avgtmp / avgcounter!!))
        } else {holder.cardMainTemp.text = holder.itemView.resources.getString(R.string.unknown_value)}

        //Draw tripLowTmp into card
        holder.cardHighTemp.text = holder.itemView.resources.getString(R.string.tempHighString, maxtmp)

        //Draw tripHighTmp into card
        holder.cardLowTemp.text = holder.itemView.resources.getString(R.string.tempLowString, mintmp)

        //Draw tripWeatherIcons into card recycler
        holder.cardWeatherIconRecycler.adapter = WeatherIconAdapter(symbols)

        //Make clickable and displayable
        holder.itemView.setOnClickListener {
            MainActivityViewModel.setDisplayedTrip(trip) //TODO: life hack, shouldn't be in MainActivityViewModel. Do better [Medium]
            findNavController(holder.itemView).navigate(R.id.action_HomeFragment_to_TripFragment)
        }
    }

    override fun getItemCount() = dataSet.size
}