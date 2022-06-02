package com.example.cleather.adapterClasses

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleather.R
import com.example.cleather.dataClasses.TripItem
import com.example.cleather.databinding.CardItemIconBinding
import com.example.cleather.enumClasses.ItemSpecialFeature

class ItemIconAdapter(private val dataSet: List<TripItem>): RecyclerView.Adapter<ItemIconAdapter.ViewHolder>() { //dataSet is a list of clothes
    class ViewHolder(binding: CardItemIconBinding) : RecyclerView.ViewHolder(binding.root) {
        var cardClothingIcon        : ImageView = binding.cardClothingIcon
        var isCheckedIcon           : ImageView = binding.isCheckedIcon
        var specialFeatureIcons     : List<ImageView> = listOf( // Icon3 be used as an expansion where some other information could be shown
            binding.specialFeatureIcon0,                        // (Ex. the clothing that has been chosen does not fit the weather it is being packed for)
            binding.specialFeatureIcon1,                        // Is list for ease of use
            binding.specialFeatureIcon2,
            binding.specialFeatureIcon3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardItemIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Setts the clothing image for the card
        holder.cardClothingIcon.setImageResource(
            holder.itemView.resources.getIdentifier(
                dataSet[position].itemGraphic, "drawable", holder.itemView.context.packageName
            )
        )

        //Setts the special feature icons
        var i = 0
        for (specialFeature in  dataSet[position].itemSpecialFeature) { //TODO: Is untested. Test [HIGH]
            //The NA special feature, could be exchanged with an empty list instead
            if (dataSet[position].itemSpecialFeature.contains(ItemSpecialFeature.NA)) continue

            val icon = holder.specialFeatureIcons[i]
            val pictureName = specialFeature.toString().lowercase()

            icon.setImageResource(
                holder.itemView.resources.getIdentifier(
                    pictureName, "drawable", holder.itemView.context.packageName
                )
            )
            i++
        }

        //Makes it look like it is checked if it is checked.
        holder.cardClothingIcon.setColorFilter(if (dataSet[position].isChecked) Color.argb(150,100,100,100) else 0 )
        holder.isCheckedIcon.setImageResource(if (dataSet[position].isChecked) R.drawable.checked else 0 )

        //Makes clothing card clickable.
        holder.itemView.setOnClickListener {
            //Checks if item is checked. If checked: display checkmark + color filter else remove checkmark + color filter
            if(!dataSet[position].isChecked) {
                dataSet[position].isChecked = true
                holder.cardClothingIcon.setColorFilter(Color.argb(150,100,100,100))
                holder.isCheckedIcon.setImageResource(R.drawable.checked)
            } else {
                dataSet[position].isChecked = false
                holder.cardClothingIcon.setColorFilter(0)
                holder.isCheckedIcon.setImageResource(0)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}