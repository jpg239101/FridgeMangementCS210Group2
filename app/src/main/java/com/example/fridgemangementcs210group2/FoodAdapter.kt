package com.example.fridgemangementcs210group2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.android.synthetic.main.fridge_item.view.*

class FoodAdapter(
    val foods: ArrayList<Food>
): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.food_item,
                parent,
                false
            )
        )
    }

    fun addFood(food: Food) {
        foods.add(food)
        notifyItemInserted(foods.size - 1)
    }

    fun deleteFood() {
        foods.removeAll { food ->
            food.isChecked
        }
        notifyDataSetChanged()
    }


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
    override fun getItemCount(): Int {
            return foods.size
    }

}