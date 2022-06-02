package com.example.cleather.adapterClasses

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleather.databinding.CardWeatherIconBinding

/*
* This adapter is responsible for showing the weather icons, in the icon recyclers.
* */
class WeatherIconAdapter(private val dataSet: MutableList<Int>): RecyclerView.Adapter<WeatherIconAdapter.ViewHolder>() {
    class ViewHolder(binding: CardWeatherIconBinding) : RecyclerView.ViewHolder(binding.root) {
        var cardWeatherIcon   : ImageView = binding.cardWeatherIcon
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardWeatherIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardWeatherIcon.setImageResource(dataSet[position])
        holder.cardWeatherIcon.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun getItemCount() = dataSet.size
}