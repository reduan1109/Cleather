package com.example.cleather.adapterClasses

import android.view.LayoutInflater
import android.view.ViewGroup
import com.prof.rssparser.Article
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleather.databinding.CardMetAlertBinding

/*
*This is the adapter for the alert card.
* */
class AlertAdapter(private val dataSet: List<Article>): RecyclerView.Adapter<AlertAdapter.ViewHolder>() {
    class ViewHolder(binding: CardMetAlertBinding) : RecyclerView.ViewHolder(binding.root) {
        //var alertIcon           : ImageView = binding.alertIcon //Not in use at the moment
        var alertTitle          : TextView  = binding.alertTitle
        var alertDescription    : TextView  = binding.alertDescription
        //var alertPlace          : TextView  = binding.alertPlace
        var alertDate           : TextView  = binding.alertDate
        //var alertColor          : View      = binding.alertColor
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardMetAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Not everything in the tittle is used
        val title = (dataSet[position].title)!!.split(",")[0]

        val keywords = (dataSet[position].title)?.replace(",", "")!!.split(",")

        //Not everything in the date is used
        val dateWords = ((dataSet[position].title)!!.split(",").last()).split(" ").toMutableList()
        dateWords.removeAt(3)
        dateWords.removeAt(3)
        dateWords.removeAt(6)
        dateWords.removeAt(6)

        //Make date
        var date = ""
        dateWords.forEach { date = "$date$it " }

        //Set title
        holder.alertTitle.text = title

        //Set date
        holder.alertDate.text =  date

        //Set description
        holder.alertDescription.text = dataSet[position].description
    }

    override fun getItemCount() = dataSet.size
}