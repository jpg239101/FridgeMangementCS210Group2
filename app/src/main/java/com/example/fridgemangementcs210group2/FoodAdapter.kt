package com.example.fridgemangementcs210group2

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.android.synthetic.main.fridge_item.view.*
import java.time.LocalDate
import java.time.LocalDate.parse

class FoodAdapter(
    //store food objects for recycler view
    val foods: ArrayList<Food>
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    //creates view holder class
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //sets the layout for recycler view to food_item.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.food_item,
                parent,
                false
            )
        )
    }

    //add a food object to the list
    fun addFood(food: Food) {
        foods.add(food)
        notifyItemInserted(foods.size - 1)
    }

    //delete a food object from the list
    fun deleteFood() {
        foods.removeAll { food ->
            food.isChecked
        }
        notifyDataSetChanged()
    }


    //gets and sets up the food_item being listed
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val curFood = foods[position]
        holder.itemView.apply {
            tvFoodTitle.text = curFood.title
            cbdel.isChecked = curFood.isChecked
            cbdel.setOnCheckedChangeListener { _, isChecked ->
                curFood.isChecked = !curFood.isChecked
            }

        }
    }

    //number of food items
    override fun getItemCount(): Int {
        return foods.size
    }

}